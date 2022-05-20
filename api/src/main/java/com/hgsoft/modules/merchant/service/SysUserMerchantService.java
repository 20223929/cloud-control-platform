package com.hgsoft.modules.merchant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.merchant.entity.SysUserMerchant;
import com.hgsoft.modules.merchant.entity.TbMerchant;
import com.hgsoft.modules.merchantcommon.MerchantTree;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 用户商户关联表Service
 * @author 吴锡霖
 * @version 2021-04-19 09:13:54
 */
public interface SysUserMerchantService extends CrudService<SysUserMerchant> {

    IPage<SysUserMerchant> sysUserMerchantPage(Page<SysUserMerchant> page, SysUserMerchant sysUserMerchant);

    SysUserMerchant getByPrimaryKey(String id);

    void saveSysUserMerchant(SysUserMerchant sysUserMerchant);

    void saveSysUserMerchant(List<SysUserMerchant> list,Integer nodeLevel);

    /**
     * 批量删除(物理删除)
     */
    void deleteByPrimaryKey(List<String> dataList);


    /**
     * 批量删除(逻辑删除)
     */
    void removeByPrimaryKey(List<String> dataList);
    /**
     * 导入
     */
    List<String> importExcel(MultipartFile file, boolean isNewPk, String strategy) throws Exception;

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, SysUserMerchant sysUserMerchant);

    /**
     * 下载数据导入模板
     */
    ModelAndView exportTemplate(HttpServletRequest request);

    SysUserMerchant userMerchant(String userId);

    TbMerchant findMerchantByUser(String userId);

    List<MerchantTree> findMerchantByUserId(String userId);
}
