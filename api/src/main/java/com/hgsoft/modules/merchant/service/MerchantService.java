package com.hgsoft.modules.merchant.service;

import com.hgsoft.modules.merchant.entity.Merchant;
import com.hgsoft.modules.merchant.entity.MerchantGroup;
import com.hgsoft.modules.merchant.entity.Site;
import com.hgsoft.modules.merchantcommon.MerchantTree;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 吴鉴武 on 2021/4/27 16:11
 */
public interface MerchantService {

    /**
     * 查找商户树
     *
     * @return
     */
    List<MerchantTree> findTreeData();

    /**
     * 保存一级商户信息
     *
     * @param merchantGroup
     */
    void saveMerchantGroup(MerchantGroup merchantGroup);

    /**
     * 保存二级商户信息
     *
     * @param certFile 商户证书信息
     * @param merchant
     */
    void saveMerchant(MultipartFile certFile, Merchant merchant);

    /**
     * 保存三级商户信息
     *
     * @param site
     */
    void saveSite(Site site);

    /**
     * 通过ID查询一级商户信息
     *
     * @param id
     * @return
     */
    MerchantGroup getMerchantGroupById(String id);

    /**
     * 通过ID查询二级商户信息
     *
     * @param id
     * @return
     */
    Merchant getMerchantById(String id);

    /**
     * 通过ID查询三级商户信息
     *
     * @param id
     * @return
     */
    Site getSiteById(String id);

    /**
     * 根据节点级别查询商户信息
     *
     * @param nodeLevel
     * @return
     */
    List<MerchantTree> findMerchantByNodeLevel(Integer nodeLevel);

    /**
     * 下载证书
     *
     * @return
     */
    byte[] downloadCert(HttpServletResponse response);
}
