package com.hgsoft.modules.merchant.service;

import com.hgsoft.modules.merchant.entity.TbMerchant;

import com.hgsoft.ecip.framework.core.service.CrudService;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 商户信息Service
 * @author 吴锡霖
 * @version 2021-02-27 00:40:22
 */
public interface TbMerchantService extends CrudService<TbMerchant> {

    List<TbMerchant> findTreeData(TbMerchant tbMerchant, String excludedId);

    TbMerchant getByPrimaryKey(String id);

    void saveTbMerchant(TbMerchant tbMerchant);
    
    void updateTbMerchant(TbMerchant tbMerchant);

    /**
     * 批量删除(物理删除)
     */
    void deleteByPrimaryKey(List<String> dataList);

    
    /**
     * 导入
     */
    List<String> importExcel(MultipartFile file, boolean isNewPk, String strategy) throws Exception;

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, TbMerchant tbMerchant);

    /**
     * 下载数据导入模板
     */
    ModelAndView exportTemplate(HttpServletRequest request);

    /**
     * 根据code获取当前所有级别的商户信息
     * @param code
     * @return
     */
    Map<String,String>  getAllLevelMerchantInfo(String code);
}