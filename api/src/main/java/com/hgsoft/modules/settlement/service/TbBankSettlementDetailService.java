package com.hgsoft.modules.settlement.service;

import com.hgsoft.modules.settlement.entity.TbBankSettlementDetail;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.ecip.framework.common.response.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 银行预结算明细Service
 * @author 何志豪
 * @version 2021-05-08 05:37:06
 */
public interface TbBankSettlementDetailService extends CrudService<TbBankSettlementDetail> {

    IPage<TbBankSettlementDetail> tbBankSettlementDetailPage(Page<TbBankSettlementDetail> page, TbBankSettlementDetail tbBankSettlementDetail);

    TbBankSettlementDetail getByPrimaryKey(String transactionId);

    void saveTbBankSettlementDetail(TbBankSettlementDetail tbBankSettlementDetail);
    
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
    ModelAndView exportData(HttpServletRequest request, TbBankSettlementDetail tbBankSettlementDetail);

    /**
     * 下载数据导入模板
     */
    ModelAndView exportTemplate(HttpServletRequest request);
}