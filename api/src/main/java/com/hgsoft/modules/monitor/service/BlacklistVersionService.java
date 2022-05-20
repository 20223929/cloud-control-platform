package com.hgsoft.modules.monitor.service;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.monitor.entity.BlacklistVersion;

import java.util.List;
/**
 * 黑名单版本监控Service
 * @author 吴鉴武
 * @version 2021-04-21 02:24:32
 */
public interface BlacklistVersionService extends CrudService<BlacklistVersion> {

    /**
     * 获取黑名单版本信息
     * @return
     */
    List<BlacklistVersion> getBlacklistVersion();
}