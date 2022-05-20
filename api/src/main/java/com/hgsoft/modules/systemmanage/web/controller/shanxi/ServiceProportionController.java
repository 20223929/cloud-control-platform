package com.hgsoft.modules.systemmanage.web.controller.shanxi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.systemmanage.entity.shanxi.ServiceProportion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 服务费分成信息管理Controller
 * @author 吴鉴武
 * @version 2021-04-25 02:49:29
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "服务费分成信息管理")
@RestController("com.hgsoft.modules.serviceproportion.web.controller.ServiceProportionController")
@RequestMapping("api/v1/serviceproportion/tbServiceProportion")
public class ServiceProportionController {

    @PostMapping("/data")
    @ApiOperation(value = "分页查询服务费分成信息管理", notes = "分页查询服务费分成信息管理")
    public ResultBean<IPage<ServiceProportion>> findPage(Page<ServiceProportion> page, @RequestBody ServiceProportion tbServiceProportion){
        return ResultHandler.ok(page.setRecords(new ArrayList<>()));
    }
}