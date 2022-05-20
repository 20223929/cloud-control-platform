package com.hgsoft.modules.monitor.mapper;

import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.monitor.entity.BlacklistVersion;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 黑名单版本监控MAPPER接口
 * @author 吴鉴武
 * @version 2021-04-21 02:24:32
 */
@Repository("com.hgsoft.modules.monitor.mapper.BlacklistVersionMapper")
public interface BlacklistVersionMapper extends DataMapper<BlacklistVersion> {

    /**
     * 获取黑名单版本信息
     * @return
     */
    List<BlacklistVersion> getBlacklistVersion();
}