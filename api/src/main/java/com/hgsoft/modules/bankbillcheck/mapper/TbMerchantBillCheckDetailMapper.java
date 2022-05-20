package com.hgsoft.modules.bankbillcheck.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.modules.bankbillcheck.entity.TbMerchantBillCheckDetail;
import com.hgsoft.modules.bankbillcheck.entity.TbMerchantBillCheckDetailSearchVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.hgsoft.modules.bankbillcheck.mapper.TbMerchantBillCheckDetailMapper")
public interface TbMerchantBillCheckDetailMapper {
    /**
     * 用于分页查询
     *
     * @param page
     * @param entity
     * @return
     */
    public IPage<TbMerchantBillCheckDetail> findSearchDataPage(IPage<TbMerchantBillCheckDetail> page,
                                                               @Param("entity") TbMerchantBillCheckDetailSearchVo entity);

    public List<TbMerchantBillCheckDetail> findSearchDataAll(@Param("entity") TbMerchantBillCheckDetailSearchVo entity);
}
