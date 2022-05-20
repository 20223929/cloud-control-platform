package com.hgsoft.modules.bankbillcheck.mapper;

import com.hgsoft.modules.bankbillcheck.entity.TbCenterBillCheckDetail;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import org.springframework.stereotype.Repository;

/**
 * 联网中心对账核对明细表MAPPER接口
 * @author 何志豪
 * @version 2021-04-21 02:34:34
 */
@Repository("com.hgsoft.modules.bankbillcheck.mapper.TbCenterBillCheckDetailMapper")
public interface TbCenterBillCheckDetailMapper extends DataMapper<TbCenterBillCheckDetail> {

}