package com.hgsoft.modules.settlementmanage.web.controller.shanxi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.settlementmanage.entity.shanxi.EtcServiceSplit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 联网中心服务费拆分结算管理Controller
 * @author 吴鉴武
 * @version 2021-04-25 01:49:40
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "联网中心服务费拆分结算管理")
@RestController("com.hgsoft.modules.settlementmanage.web.controller.shanxi.EtcServiceSplitController")
@RequestMapping("api/v1/settlementmanage/etcServiceSplit")
public class EtcServiceSplitController {

    @PostMapping("/data")
    @ApiOperation(value = "分页查询联网中心服务费拆分结算管理", notes = "分页查询联网中心服务费拆分结算管理")
    public ResultBean<IPage<EtcServiceSplit>> findPage(Page<EtcServiceSplit> page, @RequestBody EtcServiceSplit etcServiceSplit){
        return ResultHandler.ok(page.setRecords(new ArrayList<>()));
    }
}