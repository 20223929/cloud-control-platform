package com.hgsoft.modules.merchant.mapper;

import com.hgsoft.modules.merchant.entity.Merchant;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import org.springframework.stereotype.Repository;

/**
 * 二级商户信息表MAPPER接口
 * @author 吴鉴武
 * @version 2021-04-27 02:20:09
 */
@Repository("com.hgsoft.modules.merchant.mapper.MerchantMapper")
public interface MerchantMapper extends DataMapper<Merchant> {

}