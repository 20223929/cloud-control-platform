package com.hgsoft.modules.agent.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.agent.cache.AgentCache;
import com.hgsoft.modules.agent.entity.Agent;
import com.hgsoft.modules.agent.enums.AgentEnum;
import com.hgsoft.modules.agent.mapper.AgentMapper;
import com.hgsoft.modules.agent.service.AgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * 投诉退费管理serviceImpl
 * Created by 吴鉴武 on 2021/5/7 16:08
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentMapper mapper;
    private final RedisTemplate businessRedisTemplate;

    @Override
    public IPage<Agent> findPage(Page<Agent> page, Agent agent) {
        page.initOrder();
        IPage<Agent> iPage = mapper.findPage(page, agent);
        //循环填充证件类型描述
        iPage.getRecords().replaceAll(vo -> {
            vo.setCertificateTypeDesc(AgentEnum.getDescByCode(AgentEnum.CertificateType.values(), vo.getCertificateType()));
            return vo;
        });
        return iPage;
    }

    @Override
    public Agent findById(Long sysId) {
        return mapper.selectById(sysId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<String> saveAgent(Agent agent) {
        //区分编辑和新增
        if (StrUtil.isBlank(agent.getAgentNo())) {
            //新增
            //代理商名称要唯一
            Integer count = mapper.selectCount(new LambdaQueryWrapper<Agent>().eq(Agent::getAgentName, agent.getAgentName()));
            if (count > 0) {
                return ResultHandler.error("代理商名称已存在");
            }
            agent.setCreateTime(new Date());
            agent.setAgentNo(StrUtil.fillBefore(businessRedisTemplate.opsForValue().increment("agentno").toString(), '0', 6));
            try {
                mapper.insert(agent);
            }catch (Exception e){
                log.error("新增代理商失败",e);
                return ResultHandler.error("新增代理商失败:" + ExceptionUtil.getRootCauseMessage(e));
            }
        } else {
            //编辑
            //代理商名称要唯一（排除自身）
            Integer count = mapper.selectCount(new LambdaQueryWrapper<Agent>().eq(Agent::getAgentName, agent.getAgentName()).ne(Agent::getAgentNo, agent.getAgentNo()));
            if (count > 0) {
                return ResultHandler.error("代理商名称已存在");
            }
            agent.setModifyTime(new Date());
            try {
                mapper.updateById(agent);
            }catch (Exception e){
                log.error("更新代理商失败",e);
                return ResultHandler.error("更新代理商失败:" + ExceptionUtil.getRootCauseMessage(e));
            }
        }
        //更新代理商缓存
        AgentCache.addAgent(agent.getAgentNo(),agent.getAgentName());
        return ResultHandler.ok("保存成功");
    }

    @Override
    public ModelAndView exportAgent(Agent agent) {
        List<Agent> list = mapper.findList(agent);
        list.replaceAll(vo -> {
            vo.setCertificateTypeDesc(AgentEnum.getDescByCode(AgentEnum.CertificateType.values(), vo.getCertificateType()));
            return vo;
        });
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "代理商信息-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        mv.addObject(NormalExcelConstants.CLASS, Agent.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("代理商信息", "代理商信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }
}
