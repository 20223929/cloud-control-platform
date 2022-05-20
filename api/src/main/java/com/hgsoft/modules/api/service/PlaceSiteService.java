package com.hgsoft.modules.api.service;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.api.entity.PlaceSite;
import com.hgsoft.modules.api.entity.PlaceSiteVo;

import java.util.List;

public interface PlaceSiteService extends CrudService<PlaceSiteVo> {

    /**
     * 拓展场所列表查询
     * @param serviceType
     * @return
     */
    List<PlaceSite> findByServiceType(Integer serviceType);
}
