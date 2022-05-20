package com.hgsoft.modules.agent.cache;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.hgsoft.modules.agent.entity.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 *
 * 代理商缓存类
 * Created by 吴鉴武
 * date:2022/5/5 9:15
 */
@Component
public class AgentCache{

    //缓存代理商主键和名称
    private static Map<String,String> idNameMap;
    //同步状态
    private static AtomicInteger STATE = new AtomicInteger();

    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * 获取缓存
     * @return
     */
    public static Map<String,String> getAgentMap(){
        return idNameMap;
    }

    /**
     * 添加或覆盖缓存
     * @param id
     * @param name
     */
    public static void addAgent(String id,String name){
        while (STATE.compareAndSet(0,1));
        idNameMap.put(id, name);
        STATE.set(0);
    }

    @PostConstruct
    public void initMap() {
        List<Agent> agentList = template.query("select agent_no,agent_name from tb_agent",MapUtil.empty(),new BeanPropertyRowMapper<>(Agent.class));
        if(CollUtil.isEmpty(agentList)) idNameMap = new HashMap<>();
        idNameMap = agentList.stream().collect(Collectors.toMap(k->k.getAgentNo(),v->v.getAgentName()));
    }
}
