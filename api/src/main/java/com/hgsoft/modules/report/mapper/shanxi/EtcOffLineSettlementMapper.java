package com.hgsoft.modules.report.mapper.shanxi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.modules.report.entity.shanxi.EtcOffLineSettlement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtcOffLineSettlementMapper {
    List<EtcOffLineSettlement> findSearchDataAll(@Param("entity") EtcOffLineSettlement s);

    EtcOffLineSettlement sumAll(@Param("entity") EtcOffLineSettlement s);

    IPage<EtcOffLineSettlement> findSearchDataPage(Page<EtcOffLineSettlement> page,@Param("entity") EtcOffLineSettlement entity);
}
