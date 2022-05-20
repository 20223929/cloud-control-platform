package com.hgsoft.modules.report.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.modules.report.entity.ClearBill;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.report.entity.MerchantAccountReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 清分对账报表MAPPER接口
 * @author 郑裕强
 * @version 2022-05-05 02:34:08
 */
@Repository("com.hgsoft.modules.report.mapper.ClearBillMapper")
public interface ClearBillMapper extends DataMapper<ClearBill> {

    /**
     * 分页查询清分对账报表数据
     * @param iPage
     * @param clearBill
     * @param statisticsDimension
     * @return
     */
    IPage<ClearBill> clearBillPage(IPage<ClearBill> iPage, @Param("entity") ClearBill clearBill, @Param("statisticsDimension") String statisticsDimension);

    /**
     * 合计清分对账报表数据
     * @param clearBill
     * @param statisticsDimension
     * @return
     */
    ClearBill getSum(@Param("entity") ClearBill clearBill, @Param("statisticsDimension") String statisticsDimension);

    /**
     *查询清分对账报表数据
     * @param clearBill
     * @param statisticsDimension
     * @return
     */
    List<ClearBill> findList(@Param("entity") ClearBill clearBill, @Param("statisticsDimension") String statisticsDimension);
}