package com.hgsoft.modules.bankbillcheck.mapper;

import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheck;
import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheckDetail;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 银行对账明细表MAPPER接口
 * @author 何志豪
 * @version 2021-04-19 03:07:10
 */
@Repository("com.hgsoft.modules.bankbillcheck.mapper.TbBankBillCheckDetailMapper")
public interface TbBankBillCheckDetailMapper extends DataMapper<TbBankBillCheckDetail> {

    /**
     * 获取某个查询的合计
     * @param t
     */
    //public TbBankBillCheckDetail getSum(@Param("entity")TbBankBillCheckDetail t);
}