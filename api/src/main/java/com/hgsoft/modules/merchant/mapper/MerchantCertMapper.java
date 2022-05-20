package com.hgsoft.modules.merchant.mapper;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.merchant.entity.MerchantCert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 二级商户证书信息表MAPPER接口
 *
 * @author 吴鉴武
 * @version 2021-04-27 02:20:09
 */
@Repository("com.hgsoft.modules.merchant.mapper.MerchantCertMapper")
public interface MerchantCertMapper {

    /**
     * 根据商户编号查询商户证书信息
     * @param mchId
     * @return
     */
    MerchantCert selectByMchId(@Param("mchId") String mchId);

    /**
     * 插入商户证书信息
     * @param merchantCert
     */
    void insert(@Param("entity") MerchantCert merchantCert);

    /**
     * 更新商户证书信息
     * @param merchantCert
     */
    void updateMerchantCert(@Param("entity") MerchantCert merchantCert);

    /**
     * 根据商户编号删除证书信息
     * @param mchId
     */
    void deleteByMchId(@Param("mchId") String mchId);
}