package com.hgsoft.modules.api.service;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.api.entity.VehicleCount;
import com.hgsoft.modules.api.entity.VehicleCountVo;

public interface VehicleCountService extends CrudService<VehicleCountVo> {

    /**
     * 过车交易统计接口
     * @param vo
     * @return
     */
    VehicleCount vehicleCount(VehicleCountVo vo);
}
