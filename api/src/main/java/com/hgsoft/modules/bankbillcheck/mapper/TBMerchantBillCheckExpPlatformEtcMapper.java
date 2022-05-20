package com.hgsoft.modules.bankbillcheck.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.modules.bankbillcheck.entity.TBMerchantBillCheckExpPlatformEtc;
import com.hgsoft.modules.bankbillcheck.entity.TBMerchantBillCheckExpPlatformEtcSearchVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.hgsoft.modules.bankbillcheck.mapper.TBMerchantBillCheckExpPlatformEtcMapper")
public interface TBMerchantBillCheckExpPlatformEtcMapper {

    /**
     * 用于分页查询
     *
     * @param page
     * @param entity
     * @return
     */
    public IPage<TBMerchantBillCheckExpPlatformEtc> findSearchDataPage(IPage<TBMerchantBillCheckExpPlatformEtc> page,
                                                                       @Param("entity") TBMerchantBillCheckExpPlatformEtcSearchVo entity);

    /**
     * 用于导出
     *
     * @param entity
     * @return
     */
    public List<TBMerchantBillCheckExpPlatformEtc> findSearchDataAll(
            @Param("entity") TBMerchantBillCheckExpPlatformEtcSearchVo entity);

    /**
     * 获取某个查询的合计
     *
     * @param param
     */
    public TBMerchantBillCheckExpPlatformEtc getSum(@Param("entity") TBMerchantBillCheckExpPlatformEtcSearchVo param);

    public TBMerchantBillCheckExpPlatformEtc getOne(@Param("entity") TBMerchantBillCheckExpPlatformEtc param);

    public void insert(TBMerchantBillCheckExpPlatformEtc param);

    public void update(TBMerchantBillCheckExpPlatformEtc param);
}
