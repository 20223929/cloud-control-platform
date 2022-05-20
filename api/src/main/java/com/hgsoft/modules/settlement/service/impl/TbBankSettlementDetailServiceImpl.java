package com.hgsoft.modules.settlement.service.impl;

import com.hgsoft.modules.settlement.entity.TbBankSettlementDetail;
import com.hgsoft.modules.settlement.mapper.TbBankSettlementDetailMapper;
import com.hgsoft.modules.settlement.service.TbBankSettlementDetailService;

import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;

import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.framework.shiro.ShiroUser;

import com.hgsoft.ecip.web.util.SystemUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

import com.hgsoft.ecip.framework.util.IdWorkerInit;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.ExcelImportUtil;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.excel.entity.ImportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import org.apache.commons.lang3.StringUtils;

import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;



/**
 * 银行预结算明细ServiceImpl
 * @author 何志豪
 * @version 2021-05-08 05:37:06
 */

@Service("com.hgsoft.modules.settlement.service.TbBankSettlementDetailService")
public class TbBankSettlementDetailServiceImpl extends CrudServiceImpl<TbBankSettlementDetailMapper, TbBankSettlementDetail> implements TbBankSettlementDetailService {

    private final String[] tipString = {
        "transactionId(【主键transactionId】): 可以不填写主键transactionId；",
        "eexitType(【流水类型】): 必填",
        "bankTransDate(【银行交易日期】): 必填",
        "transactionId(【流水号】): 必填",
        "deductionOrderId(【银行交易检索号】): 必填",
        "vehiclePlate(【车牌号码】): 必填",
        "vehicleColor(【车牌颜色】): 必填",
        "merchantGroupId(【拓展方】): 必填",
        "merchantId(【运营方】): 必填",
        "siteId(【场景】): 必填",
        "serviceType(【服务类型】): 必填",
        "merchantRealFee(【客户实付金额】): 必填",
        "merchantDiscountFee(【优惠金额】): 必填",
        "remarks(【备注信息】): 非必填"
    };

    /**
     * 分页查询
     * @param page
     * @param tbBankSettlementDetail
     * @return
     */
    @Override
    public IPage<TbBankSettlementDetail> tbBankSettlementDetailPage(Page<TbBankSettlementDetail> page, TbBankSettlementDetail tbBankSettlementDetail) {
        tbBankSettlementDetail.setDataScope(SystemUtils.newInstance().findDataScope("tbBankSettlementDetail:page"));
        page.initOrder();
        return this.findPage(page, tbBankSettlementDetail);
    }

    @Override
    public TbBankSettlementDetail getByPrimaryKey(String transactionId) {
        return this.getById(transactionId);
    }


    @Override
    @Transactional
    public void saveTbBankSettlementDetail(TbBankSettlementDetail tbBankSettlementDetail) {
        tbBankSettlementDetail.setTransactionId(IdWorkerInit.generateIdStr());

        tbBankSettlementDetail.preInsert();
        this.saveEntity(tbBankSettlementDetail);
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
        List<TbBankSettlementDetail> list = ExcelImportUtil.importExcel(file.getInputStream(), TbBankSettlementDetail.class, params);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<TbBankSettlementDetail> saveList = new ArrayList<>();
        List<TbBankSettlementDetail> updateList = new ArrayList<>();
        List<String> dataIds = list.stream().map(TbBankSettlementDetail::getTransactionId).collect(Collectors.toList());
        List<String> existIds = new ArrayList<>();
        if (!isNewPk) {
            LambdaQueryWrapper<TbBankSettlementDetail> queryWrapper = new LambdaQueryWrapper<TbBankSettlementDetail>().select(TbBankSettlementDetail::getTransactionId);
            queryWrapper.in(TbBankSettlementDetail::getTransactionId, dataIds);
            existIds = this.list(queryWrapper).stream().map(TbBankSettlementDetail::getTransactionId).collect(Collectors.toList());
            dataIds.removeAll(existIds);    // 筛选出存在数据库Id，余下则为不存在数据库ids
        }
        for(TbBankSettlementDetail item: list) {
            // 主键策略为使用新增主键 或者 主键值为空，则认为是新增
            if (isNewPk || StringUtils.isBlank(item.getTransactionId())) {
                item.setTransactionId(IdWorkerInit.generateIdStr());
                item.preInsert();
                saveList.add(item);
            } else {    // isNewPk:false, 主键值不为空, dataIds判断是否为数据库不存在数据, 若是不存在, 则为新增数据, 且id以excel导入为准
                if (dataIds.contains(item.getTransactionId())) {
                    ShiroUser shiroUser = new ShiroUser();
                    shiroUser.setId(ShiroSecurityUtil.userId());
                    saveList.add(item);
                } else if (strategy.equals("update")) {
                    if (existIds.contains(item.getTransactionId())) {
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
        return list.stream().map(TbBankSettlementDetail::getTransactionId).collect(Collectors.toList());
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, TbBankSettlementDetail tbBankSettlementDetail) {
        List<TbBankSettlementDetail> list = this.findList(tbBankSettlementDetail);
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "银行预结算明细-" + System.currentTimeMillis());
        mv.addObject(NormalExcelConstants.CLASS, TbBankSettlementDetail.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("银行预结算明细", "银行预结算明细"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    /**
      * 导出模板
      */
    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "银行预结算明细模板");
        mv.addObject(NormalExcelConstants.CLASS, TbBankSettlementDetail.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("银行预结算明细", "银行预结算明细"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

}