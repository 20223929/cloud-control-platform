package com.hgsoft.modules.merchant.mapper;

import com.hgsoft.modules.merchant.entity.TbMerchant;

import com.hgsoft.ecip.framework.core.presistence.FlatTreeMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 商户信息MAPPER接口
 * @author 吴锡霖
 * @version 2021-02-27 00:40:22
 */
@Repository("com.hgsoft.modules.merchant.mapper.TbMerchantMapper")
public interface TbMerchantMapper extends FlatTreeMapper<TbMerchant> {

    List<TbMerchant> findAllChildNodes(@Param("ids") List<String> ids);

}