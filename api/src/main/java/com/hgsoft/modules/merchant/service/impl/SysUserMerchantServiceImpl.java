package com.hgsoft.modules.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.ExcelImportUtil;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.excel.entity.ImportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.framework.shiro.ShiroUser;
import com.hgsoft.ecip.web.util.SystemUtils;
import com.hgsoft.modules.merchant.entity.SysUserMerchant;
import com.hgsoft.modules.merchant.entity.TbMerchant;
import com.hgsoft.modules.merchant.mapper.SysUserMerchantMapper;
import com.hgsoft.modules.merchant.service.SysUserMerchantService;
import com.hgsoft.modules.merchantcommon.MerchantTree;
import com.hgsoft.modules.merchantcommon.NodeLevelEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;



/**
 * 用户商户关联表ServiceImpl
 * @author 吴锡霖
 * @version 2021-04-19 09:13:54
 */

@Service
public class SysUserMerchantServiceImpl extends CrudServiceImpl<SysUserMerchantMapper, SysUserMerchant> implements SysUserMerchantService {

    private final String[] tipString = {
        "id(【主键id】): 可以不填写主键id；",
        "userId(【用户id】): 必填",
        "merchantId(【商户id】): 必填",
        "remarks(【备注信息】): 非必填"
    };

    @Resource(name = "businessRedisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * 分页查询
     * @param page
     * @param sysUserMerchant
     * @return
     */
    @Override
    public IPage<SysUserMerchant> sysUserMerchantPage(Page<SysUserMerchant> page, SysUserMerchant sysUserMerchant) {
        sysUserMerchant.setDataScope(SystemUtils.newInstance().findDataScope("sysUserMerchant:page"));
        page.initOrder();
        return this.findPage(page, sysUserMerchant);
    }

    @Override
    public SysUserMerchant getByPrimaryKey(String id) {
        return this.getById(id);
    }


    @Override
    @Transactional
    public void saveSysUserMerchant(SysUserMerchant sysUserMerchant) {

        String userId = sysUserMerchant.getUserId();
        remove(new LambdaQueryWrapper<SysUserMerchant>().eq(SysUserMerchant::getUserId, userId));
        sysUserMerchant.preInsert();
        this.saveEntity(sysUserMerchant);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysUserMerchant(List<SysUserMerchant> list,Integer nodeLevel) {
        String userId = list.get(0).getUserId();
        remove(new LambdaQueryWrapper<SysUserMerchant>().eq(SysUserMerchant::getUserId, userId));
        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add(nodeLevel.toString());
        list.replaceAll(vo->{
            stringJoiner.add(vo.getMerchantId());
            vo.preInsert();
            return vo;
        });
        this.saveBatch(list);
        redisTemplate.boundHashOps(NodeLevelEnum.USER_AND_MERCHANT.getRedisKeyPrefix()).put(userId,stringJoiner.toString());
    }

    /**
     * 批量删除(逻辑删除)
     */
    @Transactional
    @Override
    public void removeByPrimaryKey(List<String> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        List<SysUserMerchant> removeList = new ArrayList<>();
        dataList.forEach(it -> {
            SysUserMerchant item = new SysUserMerchant();
            item.setId(it);
            item.setDelFlag("1");
            removeList.add(item);
        });
        this.updateBatchById(removeList);
    }


    /**
     * 批量删除(物理删除)
     */
    @Transactional
    @Override
    public void deleteByPrimaryKey(List<String> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        this.removeByIds(dataList);
    }

    /**
     * 导入数据
     */
    @Override
    @Transactional
    public List<String> importExcel(MultipartFile file, boolean isNewPk, String strategy) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(2);
        List<SysUserMerchant> list = ExcelImportUtil.importExcel(file.getInputStream(), SysUserMerchant.class, params);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<SysUserMerchant> saveList = new ArrayList<>();
        List<SysUserMerchant> updateList = new ArrayList<>();
        List<String> dataIds = list.stream().map(SysUserMerchant::getId).collect(Collectors.toList());
        List<String> existIds = new ArrayList<>();
        if (!isNewPk) {
            LambdaQueryWrapper<SysUserMerchant> queryWrapper = new LambdaQueryWrapper<SysUserMerchant>().select(SysUserMerchant::getId);
            queryWrapper.in(SysUserMerchant::getId, dataIds);
            existIds = this.list(queryWrapper).stream().map(SysUserMerchant::getId).collect(Collectors.toList());
            dataIds.removeAll(existIds);    // 筛选出存在数据库Id，余下则为不存在数据库ids
        }
        for(SysUserMerchant item: list) {
            // 主键策略为使用新增主键 或者 主键值为空，则认为是新增
            if (isNewPk || StringUtils.isBlank(item.getId())) {
                item.preInsert();
                saveList.add(item);
            } else {    // isNewPk:false, 主键值不为空, dataIds判断是否为数据库不存在数据, 若是不存在, 则为新增数据, 且id以excel导入为准
                if (dataIds.contains(item.getId())) {
                    ShiroUser shiroUser = new ShiroUser();
                    shiroUser.setId(ShiroSecurityUtil.userId());
                    item.setCreateBy(shiroUser);
                    item.setCreateDate(new Date());
                    item.setUpdateBy(shiroUser);
                    item.setUpdateDate(new Date());
                    saveList.add(item);
                } else if (strategy.equals("update")) {
                    if (existIds.contains(item.getId())) {
                        item.preUpdate();
                        updateList.add(item);
                    }
                }
            }
        }
        if (!saveList.isEmpty()) {
            this.saveBatch(saveList);
        }
        if (!updateList.isEmpty()) {
            this.updateBatchById(updateList);
        }
        return list.stream().map(SysUserMerchant::getId).collect(Collectors.toList());
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, SysUserMerchant sysUserMerchant) {
        List<SysUserMerchant> list = this.findList(sysUserMerchant);
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "用户商户关联表-" + System.currentTimeMillis());
        mv.addObject(NormalExcelConstants.CLASS, SysUserMerchant.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("用户商户关联表", "用户商户关联表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    /**
      * 导出模板
      */
    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "用户商户关联表模板");
        mv.addObject(NormalExcelConstants.CLASS, SysUserMerchant.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("用户商户关联表", "用户商户关联表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    @Override
    public SysUserMerchant userMerchant(String userId) {
        LambdaQueryWrapper<SysUserMerchant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserMerchant::getUserId, userId);
        List<SysUserMerchant> list = list(queryWrapper);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public TbMerchant findMerchantByUser(String userId) {
        List<TbMerchant> list = mapper.findMerchantByUser(userId);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<MerchantTree> findMerchantByUserId(String userId) {
        List<SysUserMerchant> list = mapper.selectList(new LambdaQueryWrapper<SysUserMerchant>().eq(SysUserMerchant::getUserId,userId));
        if(list.isEmpty()) return null;
        Integer nodeLevel = NodeLevelEnum.getNodeLevelByIdLength(list.get(0).getMerchantId().length());
        return list.stream().map(vo->{
            MerchantTree merchantTree = new MerchantTree();
            merchantTree.setNodeLevel(nodeLevel);
            merchantTree.setId(vo.getMerchantId());
            return merchantTree;
        }).collect(Collectors.toList());
    }
}
