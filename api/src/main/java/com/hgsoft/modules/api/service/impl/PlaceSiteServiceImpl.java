package com.hgsoft.modules.api.service.impl;

import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.modules.api.entity.PlaceSite;
import com.hgsoft.modules.api.entity.PlaceSiteVo;
import com.hgsoft.modules.api.mapper.PlaceSiteMapper;
import com.hgsoft.modules.api.service.PlaceSiteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("com.hgsoft.modules.api.service.PlaceSiteService")
public class PlaceSiteServiceImpl extends CrudServiceImpl<PlaceSiteMapper, PlaceSiteVo> implements PlaceSiteService {

    @Override
    public List<PlaceSite> findByServiceType(Integer serviceType) {
        List<PlaceSite> placeSites = new ArrayList<>();
        List<PlaceSiteVo> vos = this.mapper.findByServiceType(serviceType);

        vos.listIterator().forEachRemaining(c -> {
            PlaceSite placeSite = new PlaceSite();
            placeSite.setName(c.getName()).setService_type(Integer.valueOf(c.getServiceType())).setLat(c.getLat()).setLng(c.getLng());
            placeSites.add(placeSite);
        });

        return placeSites;
    }
}
