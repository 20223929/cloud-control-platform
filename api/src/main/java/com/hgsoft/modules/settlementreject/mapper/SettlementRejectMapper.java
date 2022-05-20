package com.hgsoft.modules.settlementreject.mapper;

import com.hgsoft.modules.settlementreject.entity.SettlementReject;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import org.springframework.stereotype.Repository;

/**
 * 退费详情表MAPPER接口
 * @author 郑裕强
 * @version 2022-05-08 03:40:27
 */
@Repository("com.hgsoft.modules.settlementreject.mapper.SettlementRejectMapper")
public interface SettlementRejectMapper extends DataMapper<SettlementReject> {

}