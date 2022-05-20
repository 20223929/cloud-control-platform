package com.hgsoft.modules.api.mapper;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.api.entity.PlaceSite;
import com.hgsoft.modules.api.entity.PlaceSiteVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.hgsoft.modules.api.mapper.PlaceSiteMapper")
public interface PlaceSiteMapper extends DataMapper<PlaceSiteVo> {

    List<PlaceSiteVo> findByServiceType(@Param("serviceType") Integer serviceType);
}
