package com.hgsoft.modules.agent.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.agent.entity.Agent;
import com.hgsoft.modules.agent.service.AgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 投诉信息处理Controller
 *
 * @author 吴鉴武
 * @version 2021-04-25 04:01:38
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "代理商控制层")
@RestController
@RequestMapping("api/v1/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    /**
     * 分页查询投诉信息处理
     *
     * @param page
     * @param agent
     * @return
     */
    @RequiresPermissions("agent:view")
    @PostMapping("/data")
    @ApiOperation(value = "分页查询代理商信息", notes = "分页查询代理商信息")
    public ResultBean<IPage<Agent>> findPage(Page<Agent> page, @RequestBody Agent agent) {
        return ResultHandler.ok(agentService.findPage(page, agent));
    }

    /**
     * 通过id获取代理商信息
     *
     * @param sysId
     * @return
     */
    @GetMapping("/getAgentByPrimaryKey/{sysId}")
    public ResultBean<Agent> getAgentByPrimaryKey(@PathVariable Long sysId) {
        return ResultHandler.ok(agentService.findById(sysId));
    }

    /**
     * 保存代理商
     *
     * @param agent
     * @return
     */
    @PostMapping("/save")
    public ResultBean<String> saveAgent(@RequestBody Agent agent) {
        return agentService.saveAgent(agent);
    }

    /**
     * 导出代理商信息
     *
     * @param agent
     * @return
     */
    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出代理商信息", notes = "导出代理商信息")
    public ModelAndView exportExcel(@RequestBody Agent agent) {
        return agentService.exportAgent(agent);
    }
}