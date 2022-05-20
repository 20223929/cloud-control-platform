package com.hgsoft.modules.agent.mapper;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.agent.entity.Agent;
import org.springframework.stereotype.Repository;

/**
 * 代理商mapper
 *
 * @author 吴鉴武
 * @version 2022-04-29 09：47：31
 */
@Repository("com.hgsoft.modules.agent.mapper.AgentMapper")
public interface AgentMapper extends DataMapper<Agent> {

}
