package com.hgsoft.modules.report.web.controller.shanxi;

import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.report.entity.MerchantAccountReport;
import com.hgsoft.modules.report.entity.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 山西商户应收金额报表Controller
 * @author 吴鉴武
 * @date 2021-04-23 03:08:01
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "商户应收金额报表")
@RestController("com.hgsoft.modules.demo.web.controller.shanxi.TbEtcTransactionEexitController")
@RequestMapping("api/v1/sx/tbEtcTransactionEexit")
public class TbEtcTransactionEexitController {

    @PostMapping("/data")
    @ApiOperation(value = "分页查询商户应收金额报表数据", notes = "分页查询商户应收金额报表数据")
    public ResultBean<PageVo<MerchantAccountReport>> findPage(PageVo<MerchantAccountReport> page, @RequestBody MerchantAccountReport queryParam){
        page.setRecords(new ArrayList<>());
        return ResultHandler.ok(page);
    }
}