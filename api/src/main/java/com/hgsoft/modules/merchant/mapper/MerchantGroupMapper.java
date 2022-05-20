package com.hgsoft.modules.merchant.mapper;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.merchant.entity.MerchantGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 一级商户信息MAPPER接口
 * @author 吴鉴武
 * @version 2021-04-27 02:14:30
 */
@Repository("com.hgsoft.modules.merchant.mapper.MerchantGroupMapper")
public interface MerchantGroupMapper extends DataMapper<MerchantGroup> {

    List<String> getIds();
}