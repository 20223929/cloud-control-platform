package com.hgsoft.modules.api.web.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.api.entity.*;
import com.hgsoft.modules.api.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


@Slf4j
@DS("exp-platform-etc")
@Api(tags = "对外接口")
@RestController("com.hgsoft.modules.api.web.controller.ExternalApi")
@RequestMapping("/api/v1/largescreen")
public class ExternalApi {

    @Autowired
    private VehicleCountService vehicleCountService;
    @Autowired
    private RecentCountService recentCountService;
    @Autowired
    private PlaceSiteService placeSiteService;
    @Autowired
    private AlarmCountService alarmCountService;
    @Autowired
    private TodayRankService todayRankService;

    /**
     * 过车交易统计接口
     * @param timeDimension 1-今天、2-今月、3-今年
     */
    @GetMapping("/vehiclecount/{time_dimension}")
    @ApiOperation(value = "过车交易统计查询", notes = "过车交易统计查询")
    public ResultBean<VehicleCount> vehicleCount(HttpServletResponse resp, @PathVariable("time_dimension") @NonNull Integer timeDimension) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        VehicleCountVo vo = new VehicleCountVo();
        Date now = DateUtil.date();
        switch (timeDimension) {
            case 1:
                vo.setBeginTime(DateUtil.beginOfDay(now)).setEndTime(DateUtil.endOfDay(now));
                break;
            case 2:
                vo.setBeginTime(DateUtil.beginOfMonth(now)).setEndTime(DateUtil.endOfMonth(now));
                break;
            case 3:
                vo.setBeginTime(DateUtil.beginOfYear(now)).setEndTime(DateUtil.endOfYear(now));
                break;
        }
        return ResultHandler.ok(vehicleCountService.vehicleCount(vo));
    }

    /**
     * 最近12个月交易流量金额统计接口
     * @param
     */
    @GetMapping("/recentcount")
    @ApiOperation(value = "最近12个月交易流量金额统计", notes = "最近12个月交易流量金额统计")
    public ResultBean<RecentCount> recentCount() {
        return ResultHandler.ok(recentCountService.findByTime());

    }

    /**
     * 拓展场所列表查询接口
     * @param
     */
    @GetMapping("/placelist/{service_type}")
    @ApiOperation(value = "拓展场所列表查询", notes = "拓展场所列表查询")
    public ResultBean<List<PlaceSite>> placeList(@PathVariable("service_type") Integer serviceType) {
        return ResultHandler.ok(placeSiteService.findByServiceType(serviceType));
    }

    /**
     * 异常查询和统计接口
     * @param
     */
    @GetMapping("/alarmcount")
    @ApiOperation(value = "异常查询和统计", notes = "异常查询和统计")
    public ResultBean<AlarmCount> alarmCount() {
        return ResultHandler.ok(alarmCountService.findAlarmCount());
    }

    /**
     * 今日交易额排行查询接口
     * @param
     */
    @GetMapping("/todayrank")
    @ApiOperation(value = "今日交易额排行查询", notes = "今日交易额排行查询")
    public ResultBean<TodayRank> todayRank() {
        TodayRankVo vo = new TodayRankVo();
        DateTime now = DateUtil.date();
        vo.setBeginTime(DateUtil.beginOfDay(now)).setEndTime(DateUtil.endOfDay(now));

        return ResultHandler.ok(todayRankService.findTodayRank(vo));
    }
}
