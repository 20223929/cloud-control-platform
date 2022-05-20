package com.hgsoft.modules.settlement.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.modules.bankbillcheck.entity.TbEtcTransactionEexitExportExeclVo;
import com.hgsoft.modules.settlement.entity.SettlementSearchVo;
import com.hgsoft.modules.settlement.entity.TbCenterSettlement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 联网中心结算MAPPER接口
 * @author 何志豪
 * @version 2021-05-08 04:55:35
 */
@Repository("com.hgsoft.modules.settlement.mapper.TbCenterSettlementMapper")
public interface TbCenterSettlementMapper {
   IPage<TbCenterSettlement> findPage(IPage<TbCenterSettlement> var1,@Param("entity") SettlementSearchVo entity);
   List<TbCenterSettlement> findAllList(@Param("entity") SettlementSearchVo entity);

   /**
    * 全部记录的汇总
    * @param billCheck
    * @return
    */
   TbCenterSettlement getSum(@Param("entity") SettlementSearchVo billCheck);

   /**
    * 更新结算确认信息
    * @param e
    */
   void updateSettlementConfirm(@Param("entity") TbCenterSettlement e);


   IPage<TbEtcTransactionEexitExportExeclVo> findEexitPage(Page<TbEtcTransactionEexitExportExeclVo> page, @Param("entity") SettlementSearchVo vo);

   List<TbEtcTransactionEexitExportExeclVo> findEexitAll(@Param("entity") SettlementSearchVo vo);

    Long countEexit(@Param("entity") SettlementSearchVo vo);
}