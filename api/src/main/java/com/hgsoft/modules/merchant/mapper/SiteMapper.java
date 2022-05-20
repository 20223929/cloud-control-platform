package com.hgsoft.modules.merchant.mapper;

import com.hgsoft.modules.merchant.entity.Site;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 三级商户信息表MAPPER接口
 * @author 吴鉴武
 * @version 2021-04-27 02:21:57
 */
@Repository("com.hgsoft.modules.merchant.mapper.SiteMapper")
public interface SiteMapper extends DataMapper<Site> {
    List<String> findSiteIdByAgentId(String agentId);
}