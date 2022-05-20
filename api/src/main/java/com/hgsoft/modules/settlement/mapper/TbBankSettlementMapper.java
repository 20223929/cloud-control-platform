package com.hgsoft.modules.settlement.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.settlement.entity.TbBankSettlement;
import com.hgsoft.modules.settlement.entity.TbBankSettlementDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 银行预结算MAPPER接口
 * @author 何志豪
 * @version 2021-05-08 05:23:48
 */
@Repository("com.hgsoft.modules.settlement.mapper.TbBankSettlementMapper")
public interface TbBankSettlementMapper extends DataMapper<TbBankSettlement> {

    /**
     * 分页查询银行预结算数据
     * @param iPage
     * @param tbBankSettlement
     * @return
     */
    @Override
    PageVo<TbBankSettlement> findPage(IPage<TbBankSettlement> iPage, @Param("entity") TbBankSettlement tbBankSettlement);

    /**
     * 查询银行预结算数据列表
     * @param tbBankSettlement
     * @return
     */
    List<TbBankSettlement> findList(@Param("entity") TbBankSettlement tbBankSettlement);

    /**
     * 获取汇总信息
     * @param tbBankSettlement
     * @return
     */
    TbBankSettlement getSum( @Param("entity") TbBankSettlement tbBankSettlement);

    /**
     * 分页查询预结算明细信息
     * @param iPage
     * @param detail
     * @return
     */
    PageVo<TbBankSettlementDetail> findDetailPage(IPage<TbBankSettlementDetail> iPage, @Param("entity") TbBankSettlementDetail detail);

    /**
     * 获取明细汇总信息
     * @param TbBankSettlementDetail
     * @return
     */
    TbBankSettlementDetail getDetailSum( @Param("entity") TbBankSettlementDetail TbBankSettlementDetail);

    /**
     * 查询银行预结算明细数据列表
     * @param detail
     * @return
     */
    List<TbBankSettlementDetail> findDetailList(@Param("entity") TbBankSettlementDetail detail);
}