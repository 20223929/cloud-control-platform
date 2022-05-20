package com.hgsoft.modules.agent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.modules.agent.entity.Agent;
import org.springframework.web.servlet.ModelAndView;

/**
 * 代理商service
 * Created by 吴鉴武 on 2022-04-29 09:47:31
 */
public interface AgentService {

    /**
     * 根据条件分页查询代理商信息
     * @param page
     * @param agent
     * @return
     */
    IPage<Agent> findPage(Page<Agent> page, Agent agent);

    /**
     * 通过主键获取代理商数据
     * @param sysId
     * @return
     */
    Agent findById(Long sysId);

    /**
     * 保存代理商信息
     * @param agent
     * @return
     */
    ResultBean<String> saveAgent(Agent agent);

    /**
     * 导出代理商信息
     * @param agent
     * @return
     */
    ModelAndView exportAgent(Agent agent);
}
