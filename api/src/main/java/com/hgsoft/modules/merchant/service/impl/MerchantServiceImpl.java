package com.hgsoft.modules.merchant.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.*;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.config.ExpPlatformConfig;
import com.hgsoft.modules.config.IdCenterConfig;
import com.hgsoft.modules.config.MerchantConfig;
import com.hgsoft.modules.merchant.entity.*;
import com.hgsoft.modules.merchant.mapper.*;
import com.hgsoft.modules.merchant.service.MerchantService;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.merchantcommon.MerchantTree;
import com.hgsoft.modules.merchantcommon.NodeLevelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.security.util.KeyUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 吴鉴武 on 2021/4/27 16:11
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantGroupMapper merchantGroupMapper;
    @Autowired
    private MerchantMapper merchantMapper;
    @Autowired
    private MerchantCertMapper merchantCertMapper;
    @Autowired
    private SiteMapper siteMapper;
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private SiteChannelMapper siteChannelMapper;
    @Autowired
    private MerchantManageService merchantManageService;
    @Autowired
    private MerchantConfig merchantConfig;
    @Autowired
    private ExpPlatformConfig expPlatformConfig;
    @Autowired
    private IdCenterConfig idCenterConfig;
    @Resource(name = "businessRedisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public List<MerchantTree> findTreeData() {
        return merchantManageService.getMerchantTree(ShiroSecurityUtil.userId(), ShiroSecurityUtil.isSuperUser());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMerchantGroup(MerchantGroup merchantGroup) {
        Map<String, Object> map;
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.MERCHANT_GROUP.getRedisKeyPrefix());
        if (StrUtil.isNotBlank(merchantGroup.getPlatformMerchantGroupId())) {//更新
            merchantGroup.setModifier(ShiroSecurityUtil.getPrincipal().getUsername());
            merchantGroup.setModifytime(new Date());
            merchantGroupMapper.updateById(merchantGroup);
            map = new HashMap<>();
            BeanUtil.beanToMap(merchantGroup, map, true, key -> merchantGroup.getPlatformMerchantGroupId() + StrUtil.COLON + key);
        } else {
            Long seq = redisTemplate.opsForValue().increment(merchantConfig.getMerchantGroupSeq(), 1L);
            String platformMerchantGroupId = merchantConfig.getProvinceId() + StrUtil.fillBefore(seq.toString(), '0', 2);
            Date date = new Date();
            String username = ShiroSecurityUtil.getPrincipal().getUsername();
            merchantGroup.setPlatformMerchantGroupId(platformMerchantGroupId);
            merchantGroup.setBankMerchantGroupId(merchantConfig.getBankMerchantGroupId());
            merchantGroup.setCreator(username);
            merchantGroup.setCreatetime(date);
            merchantGroup.setModifier(username);
            merchantGroup.setModifytime(date);
            merchantGroupMapper.insert(merchantGroup);
            map = new HashMap<>();
            BeanUtil.beanToMap(merchantGroup, map, true, key -> platformMerchantGroupId + StrUtil.COLON + key);
            boundHashOperations.put(platformMerchantGroupId, platformMerchantGroupId);
        }
        boundHashOperations.putAll(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMerchant(MultipartFile certFile, Merchant merchant) {
        Map<String, Object> map;
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.MERCHANT.getRedisKeyPrefix());
        BoundHashOperations certHashOperations = redisTemplate.boundHashOps(merchantConfig.getHashKeyPrefix() + StrUtil.COLON + merchantConfig.getMerchantCertHashKeySuffix());
        String merchantId = merchant.getPlatformMerchantId();
        if (StrUtil.isNotBlank(merchantId)) {
            Integer centerIdCount = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getCenterMerchantId, merchant.getCenterMerchantId()).ne(Merchant::getPlatformMerchantId, merchantId));
            Assert.isFalse(centerIdCount > 0, "部中心编码已存在");

            Integer bankIdCount = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getBankMerchantId, merchant.getBankMerchantId()).ne(Merchant::getPlatformMerchantId, merchantId));
            Assert.isFalse(bankIdCount > 0, "银行商户编码已存在");

            String username = ShiroSecurityUtil.getPrincipal().getUsername();
            Date date = new Date();

            //判断是否修改了证书
            MerchantCert merchantCertDb = null;
            MerchantCert merchantCert = null;
            if (merchant.getIsNewFile()) {
                //修改之前的证书信息（数据库记录）
                merchantCertDb = merchantCertMapper.selectByMchId(merchantId);
                Assert.notNull(merchantCertDb, "原证书信息在数据库中不存在");

                //校验获取证书信息
                merchantCert = this.validateCertFile(certFile, merchant);

                //设置商户证书信息入库修改
                merchantCert.setUpdatedBy(username);
                merchantCert.setUpdatedTime(date.toInstant());
                merchantCertMapper.updateMerchantCert(merchantCert);
            }

            //设置商户修改信息
            merchant.setModifier(username);
            merchant.setModifytime(date);
            merchantMapper.updateById(merchant);

            //商户修改信息刷redis
            map = new HashMap<>();
            BeanUtil.beanToMap(merchant, map, true, key -> merchantId + StrUtil.COLON + key);
            map.remove(merchantId + StrUtil.COLON + "certFile");
            boundHashOperations.putAll(map);

            //商户证书信息刷redis
            if (merchantCertDb != null) {
                //删除原证书信息
                map.clear();
                String serialNoDb = merchantCertDb.getSerialNo();
                String serialNo = merchantCert.getSerialNo();
                Object[] keyArray = BeanUtil.beanToMap(merchantCertDb, map, true, key -> key + StrUtil.COLON + serialNoDb).keySet().toArray();
                certHashOperations.delete(merchantId);
                certHashOperations.delete(keyArray);

                //新的证书信息刷redis
                map.clear();
                BeanUtil.beanToMap(merchantCert, map, true, key -> key + StrUtil.COLON + serialNo);
                certHashOperations.put(merchantId + StrUtil.COLON + merchantConfig.getCertFileNameKeySuffix(), certFile.getOriginalFilename());
                certHashOperations.putAll(map);
            }
        } else {
            Integer centerIdCount = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getCenterMerchantId, merchant.getCenterMerchantId()));
            Assert.isFalse(centerIdCount > 0, "部中心编码已存在");

            Integer bankIdCount = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getBankMerchantId, merchant.getBankMerchantId()));
            Assert.isFalse(bankIdCount > 0, "银行商户编码已存在");

            Long seq = redisTemplate.opsForValue().increment(StrUtil.format(merchantConfig.getMerchantSeq(), merchant.getPlatformMerchantGroupId()), 1L);
            String platformMerchantId = merchant.getPlatformMerchantGroupId() + StrUtil.fillBefore(merchant.getServiceType().toString(), '0', 2) + StrUtil.fillBefore(seq.toString(), '0', 3);
            merchant.setPlatformMerchantId(platformMerchantId);
            Date date = new Date();
            String username = ShiroSecurityUtil.getPrincipal().getUsername();

            //校验获取证书信息
            final MerchantCert merchantCert = this.validateCertFile(certFile, merchant);

            //设置商户信息入库
            merchant.setCreator(username);
            merchant.setCreatetime(date);
            merchant.setModifier(username);
            merchant.setModifytime(date);
            merchantMapper.insert(merchant);

            //设置商户证书信息入库
            merchantCert.setCreatedBy(username);
            merchantCert.setCreatedTime(date.toInstant());
            merchantCert.setDelFlag(1);
            merchantCertMapper.insert(merchantCert);

            //新增渠道信息，2022-04-21
            Channel channel = new Channel();
            channel.setChannelId(platformMerchantId);
            channel.setChannelName(merchant.getName());
            channel.setCreatedBy(username);
            channel.setCreatedTime(date.toInstant());
            channel.setDelFlag(1);
            channel.setStatus(1);
            channelMapper.insert(channel);

            //商户信息刷redis
            map = new HashMap<>();
            BeanUtil.beanToMap(merchant, map, true, key -> platformMerchantId + StrUtil.COLON + key);
            map.remove(platformMerchantId + StrUtil.COLON + "certFile");
            boundHashOperations.put(platformMerchantId, platformMerchantId);
            boundHashOperations.putAll(map);
            BoundHashOperations merchantGroupOperations = redisTemplate.boundHashOps(NodeLevelEnum.MERCHANT_GROUP.getRedisKeyPrefix());
            String childrenKey = merchant.getPlatformMerchantGroupId() + ":children";
            if (merchantGroupOperations.hasKey(childrenKey)) {
                String child = merchantGroupOperations.get(childrenKey) + "," + platformMerchantId;
                merchantGroupOperations.put(childrenKey, child);
            } else {
                merchantGroupOperations.put(childrenKey, platformMerchantId);
            }

            //商户证书信息刷redis
            map.clear();
            BeanUtil.beanToMap(merchantCert, map, true, key -> key + StrUtil.COLON + merchantCert.getSerialNo());
            certHashOperations.put(platformMerchantId + StrUtil.COLON + merchantConfig.getCertFileNameKeySuffix(), certFile.getOriginalFilename());
            certHashOperations.putAll(map);

            //渠道信息信息刷redis
            map.clear();
            BeanUtil.beanToMap(channel, map, true, key -> key + StrUtil.COLON + channel.getChannelId());
            redisTemplate.opsForHash().putAll(merchantConfig.getHashKeyPrefix() + StrUtil.COLON + merchantConfig.getChannelHashKeySuffix(), map);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSite(Site site) {
        Map<String, Object> map;
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.SITE.getRedisKeyPrefix());
        if (StrUtil.isNotBlank(site.getPlatformSiteId())) {
            Integer centerIdCount = siteMapper.selectCount(new LambdaQueryWrapper<Site>().eq(Site::getCenterSiteId, site.getCenterSiteId()).ne(Site::getPlatformSiteId, site.getPlatformSiteId()));
            Assert.isFalse(centerIdCount > 0, "部中心编码已存在");
            //Integer bankIdCount = siteMapper.selectCount(new LambdaQueryWrapper<Site>().eq(Site::getBankSiteId, site.getBankSiteId()).ne(Site::getPlatformSiteId, site.getPlatformSiteId()));
            //Assert.isFalse(bankIdCount > 0, "银行商户编码已存在");
            site.setModifier(ShiroSecurityUtil.getPrincipal().getUsername());
            site.setModifytime(new Date());
            siteMapper.updateById(site);

            map = new HashMap<>();
            BeanUtil.beanToMap(site, map, true, key -> site.getPlatformSiteId() + StrUtil.COLON + key);
            boundHashOperations.putAll(map);
        } else {
            Integer centerIdCount = siteMapper.selectCount(new LambdaQueryWrapper<Site>().eq(Site::getCenterSiteId, site.getCenterSiteId()));
            Assert.isFalse(centerIdCount > 0, "部中心编码已存在");
            //Integer bankIdCount = siteMapper.selectCount(new LambdaQueryWrapper<Site>().eq(Site::getBankSiteId, site.getBankSiteId()));
            //Assert.isFalse(bankIdCount > 0, "银行商户编码已存在");
            Long seq = redisTemplate.opsForValue().increment(StrUtil.format(merchantConfig.getSiteSeq(), site.getPlatformMerchantId()), 1L);
            String platformSiteId = site.getPlatformMerchantId() + StrUtil.fillBefore(seq.toString(), '0', 4);
            Date date = new Date();
            String username = ShiroSecurityUtil.getPrincipal().getUsername();
            site.setPlatformSiteId(platformSiteId);
            site.setCreator(username);
            site.setCreatetime(date);
            site.setModifier(username);
            site.setModifytime(date);
            siteMapper.insert(site);

            //建立渠道与交易场所的关系
            SiteChannel siteChannel = new SiteChannel();
            siteChannel.setChannelId(site.getPlatformMerchantId());
            siteChannel.setCreatedBy(username);
            siteChannel.setCreatedTime(date.toInstant());
            siteChannel.setDelFlag(1);
            siteChannel.setMchExpSiteId(platformSiteId);
            siteChannel.setStatus(1);
            siteChannelMapper.insert(siteChannel);


            map = new HashMap<>();
            BeanUtil.beanToMap(site, map, true, key -> platformSiteId + StrUtil.COLON + key);
            boundHashOperations.put(platformSiteId, platformSiteId);
            boundHashOperations.putAll(map);
            BoundHashOperations merchantOperations = redisTemplate.boundHashOps(NodeLevelEnum.MERCHANT.getRedisKeyPrefix());
            String childrenKey = site.getPlatformMerchantId() + ":children";
            if (merchantOperations.hasKey(childrenKey)) {
                String child = merchantOperations.get(childrenKey) + "," + platformSiteId;
                merchantOperations.put(childrenKey, child);
            } else {
                merchantOperations.put(childrenKey, platformSiteId);
            }

            //渠道信息和交易场所与渠道关系信息刷redis
            map.clear();
            BeanUtil.beanToMap(siteChannel, map, true, key -> key + StrUtil.COLON + platformSiteId);
            redisTemplate.opsForHash().putAll(merchantConfig.getHashKeyPrefix() + StrUtil.COLON + merchantConfig.getSiteChannelHashKeySuffix(), map);
        }
    }

    @Override
    public MerchantGroup getMerchantGroupById(String id) {
        return merchantGroupMapper.selectById(id);
    }

    @Override
    public Merchant getMerchantById(String id) {
        Merchant merchant = merchantMapper.selectById(id);
        Object fileName = redisTemplate.boundHashOps(merchantConfig.getHashKeyPrefix() + StrUtil.COLON + merchantConfig.getMerchantCertHashKeySuffix()).get(id + StrUtil.COLON + merchantConfig.getCertFileNameKeySuffix());
        merchant.setCertFileName(ObjectUtil.isNull(fileName) ? null : fileName.toString());
        return merchant;
    }

    @Override
    public Site getSiteById(String id) {
        return siteMapper.selectById(id);
    }

    @Override
    public List<MerchantTree> findMerchantByNodeLevel(Integer nodeLevel) {
        switch (nodeLevel.intValue()) {
            case 1:
                List<MerchantGroup> merchantGroups = merchantGroupMapper.selectList(new LambdaQueryWrapper<MerchantGroup>().select(MerchantGroup::getPlatformMerchantGroupId, MerchantGroup::getName));
                return merchantGroups.stream().map(vo -> {
                    MerchantTree merchantTree = new MerchantTree();
                    merchantTree.setId(vo.getPlatformMerchantGroupId());
                    merchantTree.setName(vo.getName());
                    return merchantTree;
                }).collect(Collectors.toList());
            case 2:
                List<Merchant> merchants = merchantMapper.selectList(new LambdaQueryWrapper<Merchant>().select(Merchant::getPlatformMerchantId, Merchant::getName));
                return merchants.stream().map(vo -> {
                    MerchantTree merchantTree = new MerchantTree();
                    merchantTree.setId(vo.getPlatformMerchantId());
                    merchantTree.setName(vo.getName());
                    return merchantTree;
                }).collect(Collectors.toList());
            default:
                return null;
        }
    }

    @Override
    public byte[] downloadCert(HttpServletResponse response) {
        File file = FileUtil.file(expPlatformConfig.getCertPath());
        if (!FileUtil.exist(file)) {
            throw ExceptionUtil.wrapRuntime("文件不存在");
        }
        response.setHeader("content-disposition", "attachment;filename=" + file.getName());
        response.setHeader("content-encoding", CharsetUtil.UTF_8);
        return FileUtil.readBytes(file);
    }

    /**
     * 校验证书文件并解析返回商户证书信息
     *
     * @param merchant
     * @return
     */
    private MerchantCert validateCertFile(MultipartFile certFile, Merchant merchant) {
        //文件判空
        Assert.isFalse(certFile.isEmpty(), "证书文件为空！");

        //1.解压
        String certFileUnzipPath = merchantConfig.getCertFilePath() + File.separator + merchant.getPlatformMerchantId() + File.separator + DateUtil.today();
        if (FileUtil.exist(certFileUnzipPath)) {
            FileUtil.del(certFileUnzipPath);
        }
        try {
            ZipUtil.unzip(certFile.getInputStream(), FileUtil.file(certFileUnzipPath), CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            throw ExceptionUtil.wrapRuntime("文件解压失败" + ExceptionUtil.getSimpleMessage(e));
        }

        //2.判断文件数量和文件后缀
        List<File> files = FileUtil.loopFiles(certFileUnzipPath);
        Assert.isFalse(CollUtil.isEmpty(files) || files.size() != 2 || files.stream().filter(file -> StrUtil.endWithAny(file.getName(), "crt", "cer", "txt")).count() != 2, "压缩包内应包含且仅包含证书文件（crt或者cer）和数字签名文件(txt)");

        //4.解析证书，得到算法和公钥，验证算法和公钥长度是否符合要求，同时拿出序列号
        X509Certificate certificate;
        try (FileInputStream fileInputStream = new FileInputStream(files.stream().filter(file -> StrUtil.endWithAny(file.getName(), "crt", "cer")).findFirst().get())) {
            certificate = (X509Certificate) SecureUtil.readX509Certificate(fileInputStream);
        } catch (Exception e) {
            throw ExceptionUtil.wrapRuntime("证书文件解析失败" + ExceptionUtil.getSimpleMessage(e));
        }
        final PublicKey publicKey = certificate.getPublicKey();
        final String algorithm = publicKey.getAlgorithm();
        Assert.isTrue(merchantConfig.getAlgName().equalsIgnoreCase(algorithm) && merchantConfig.getPublicKeyLength().intValue() == KeyUtil.getKeySize(publicKey), "证书算法必须为RSA且公钥长度需要为2048位!");

        //5.验证数字签名
        final List<String> list = FileUtil.readLines(files.stream().filter(file -> StrUtil.endWithAny(file.getName(), "txt")).findFirst().get(), CharsetUtil.CHARSET_UTF_8);
        Assert.isTrue(list.size() == 2, "数字签名文件内容应包含且只包含两行字符串，第一行为源字符串，第二行为加签后的字符串!");
        final Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, null, publicKey.getEncoded());
        Assert.isTrue(sign.verify(list.get(0).getBytes(CharsetUtil.CHARSET_UTF_8), Base64Decoder.decode(list.get(1))), "数字签名文件验签不通过!");

        //6.设置商户证书信息并返回
        MerchantCert merchantCert = new MerchantCert();
        merchantCert.setSerialNo(certificate.getSerialNumber().toString());
        merchantCert.setDescription(merchant.getName() + "证书");
        merchantCert.setPublicKey(Base64Encoder.encode(publicKey.getEncoded()));
        merchantCert.setMchId(merchant.getPlatformMerchantId());
        merchantCert.setStartTime(DateUtil.formatDate(certificate.getNotBefore()));
        merchantCert.setEndTime(DateUtil.formatDate(certificate.getNotAfter()));
        merchantCert.setName(merchantCert.getDescription());
        return merchantCert;
    }

    public static void main(String[] args) {
//        File file = new File("D:\\data\\胡凯\\hgits_pkcs8_private.key");
//        byte[] bytes;
//        try {
//            bytes = FileUtil.readBytes(file);
//        } catch (Exception var10) {
//            throw new IllegalArgumentException(String.format("私钥解析错误，文件路径\"%s\"", file.getAbsolutePath()), var10);
//        }
//
//        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
//
//        KeyFactory kf;
//        try {
//            kf = KeyFactory.getInstance(AsymmetricAlgorithm.RSA.getValue());
//        } catch (NoSuchAlgorithmException var9) {
//            throw new IllegalArgumentException("私钥不支持RSA算法");
//        }
//
//        byte[] encoded;
//        try {
//            encoded = kf.generatePrivate(spec).getEncoded();
//        } catch (InvalidKeySpecException var8) {
//            throw new IllegalArgumentException("私钥无效");
//        }
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCsMhfbRTwx8yCqZWAI3xEpmp0p4AfDvAYyqCjFpCIID+NnJMBRLIxUvXaBP2CbH24BiS8c0r+Ap/CwCtf+1TUvrG/+yjNcrvKPNiF7eWu4zcfx47iA/FFXX3wVleARBdqvr1+j7CDvFCTjxs27gBTRcd8Ljo2L7PF/GdAJpsQ8TV+xSs7/4WaDsQccev+lWC7/E/DX89yRk2n0PkTRp1oWsJixR2BAJc5LGxayHmf4WjsvUNJQcNpomvMX1NRtYMk9gb/giu13zd24LU66MGpNWhkT/vLgsUzQaZO2zF6/7F8DrnsSZDqEr3ctG+hsuzsdESt3GQMxeajSE9fg22axAgMBAAECggEBAKWZHxw+Px+CQ13bVDnz3Bt4l8BXtN/mCMfatCsuJ5Q9Q2JEE7d88g/kjPzLKGrGFRLhDS/Y9VYTLAMAyqxF+no4iRQs/KwbiyVy10xZfuEg9TKI3z0calVufHR9yBhW2vKt43pPcXS6mKd83wl8qYiqy+ffVfzFB5JGDq3ZdUrPQglxSASpdiQ0DugydZdOzdjdFMBOuuGPdDDDy1yZvEBowa78CANgv8D5rVT9r45h7pgrs94OX3nf8erCrRsDliEHFTuTv9xrwi5TJPCUcpz3GSUyiMvBLGZpeug01xp6mtskTL2BRWO8nGWJbBXs8b7OJ2AUXlJfUHEoG0PxlAECgYEA211WwY2gUZocCQTqUR591DI/pF2aSIXJLB7UgZGlKM3nIG+spo8tIqdt5U2yp3SLihomkQjSER4repiiDOhs86uw7rEdjt6bEq9LJjNsnTl7RmbGrEXYopPeLalZdE3d5MHQBtHKc8pRdg7gplGVlpJIbam/Hc3MrqXlptmX6RECgYEAyPQa+ZvPB+AXwQ7kRleWisNtLc46gCZtEILR+vGtEdQ7sAi/jckURzEYU34Yd2CmgAo2lBGUtO6TZOyWuZas6QoY7fbwi0ksj1FKxwUERgQ5t8MId9+fAs1Q9cLHCe98klxz8haTvJ48LVFzo0RvblefNnH8V7n5eWMBLUAso6ECgYEAjwx6E7P4lN4rZBJJfuakZWjzKYtEngBoZJYiO/AvRMGo8/T/IIqDg3KtbnfRhfOxPzi4Z6HZ4wHKlqLMIiC7U9dlhPII9W7irzeSiPHF5W1jn1BMRHEzySkmHJ6vz/ErPw7ISnoqTIHlx1gCVVMkelEgXCryj0vyKv3SGZERyBECgYBsb2xu7KiWs1GeF48Odg0B69pqMTJYHGQV2MG+C9M8H63lm5Lhy3NpxxA9XXmOUVUHGnvBAouf5m0atJ/QnYHIbm5tWicKwf2NiGw1YHuh3H3YryFOrhh7xpa9RtFohxZNVB4ssAogabJHMgCYRUFr5VRAaNml7PC1k7r60/pCgQKBgB/BQ1MCWhPqMaTOxZhWG9woVqXN0VCc3MAa8mlWGO8dqPiRGdIAZ0jFcEkVBDsE/1WEwOmlpc2u9Rx5KcqGDfISZywxBjiOzuemuy4DoRkC+BfUIygBei3e6edm1CMeduvE+iz+/xSMw4A5XtDxRyjU0EbyiPrEkKEaewKFsRus";
        final Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, privateKey, null);
        System.out.println(Base64Encoder.encode(sign.sign("测试另一个证书".getBytes(CharsetUtil.CHARSET_UTF_8))));
    }
}
