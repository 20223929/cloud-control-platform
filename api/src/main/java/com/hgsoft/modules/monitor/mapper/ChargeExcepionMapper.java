package com.hgsoft.modules.monitor.mapper;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.monitor.entity.ChargeExcepion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收费异常管理MAPPER接口
 * @author 吴鉴武
 * @version 2021-04-21 22:35:10
 */
@Repository("com.hgsoft.modules.monitor.mapper.ChargeExcepionMapper")
public interface ChargeExcepionMapper extends DataMapper<ChargeExcepion> {
    /**
     * 根据条件查询列表信息
     * @param chargeExcepion
     * @return
     */
    List<ChargeExcepion> findListByCondition(@Param("entity") ChargeExcepion chargeExcepion);

    /**
     * 确认脱机收费异常记录
     * @param list
     * @param chargeExcepion
     */
    void confirmEtc(@Param("list") List<ChargeExcepion> list,@Param("entity") ChargeExcepion chargeExcepion);

    /**
     * 确认联机收费异常记录
     * @param list
     * @param chargeExcepion
     */
    void confirmOnline(@Param("list") List<ChargeExcepion> list,@Param("entity") ChargeExcepion chargeExcepion);
}