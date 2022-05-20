package com.hgsoft.modules.merchant.mapper;

import com.hgsoft.modules.merchant.entity.SysUserMerchant;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.merchant.entity.TbMerchant;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户商户关联表MAPPER接口
 * @author 吴锡霖
 * @version 2021-04-19 09:13:54
 */
public interface SysUserMerchantMapper extends DataMapper<SysUserMerchant> {

    List<TbMerchant> findMerchantByUser(String userId);
}
