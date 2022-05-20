package com.hgsoft.modules.monitor.web.controller;

import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.monitor.entity.BlacklistVersion;
import com.hgsoft.modules.monitor.service.BlacklistVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 黑名单版本监控Controller
 * @author 吴鉴武
 * @version 2021-04-21 02:24:32
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "黑名单版本监控")
@RestController("com.hgsoft.modules.monitor.web.controller.BlacklistVersionController")
@RequestMapping("api/v1/monitor/blacklistVersion")
public class BlacklistVersionController {

    @Autowired
    private BlacklistVersionService blacklistVersionService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询黑名单版本监控", notes = "分页查询黑名单版本监控")
    public ResultBean<List<BlacklistVersion>> findPage(){
        return ResultHandler.ok(blacklistVersionService.getBlacklistVersion());
    }
}