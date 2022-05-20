package com.hgsoft.modules.settlement.mapper;

import com.hgsoft.modules.settlement.entity.TbBankSettlementDetail;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import org.springframework.stereotype.Repository;

/**
 * 银行预结算明细MAPPER接口
 * @author 何志豪
 * @version 2021-05-08 05:37:06
 */
@Repository("com.hgsoft.modules.settlement.mapper.TbBankSettlementDetailMapper")
public interface TbBankSettlementDetailMapper extends DataMapper<TbBankSettlementDetail> {

}