package com.hgsoft.modules.api.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.modules.api.entity.RecentCount;
import com.hgsoft.modules.api.entity.RecentCountVo;
import com.hgsoft.modules.api.mapper.RecentCountMapper;
import com.hgsoft.modules.api.service.RecentCountService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("com.hgsoft.modules.api.service.RecentCountService")
public class RecentCountServiceImpl extends CrudServiceImpl<RecentCountMapper, RecentCountVo> implements RecentCountService {

    @Override
    public RecentCount findByTime() {
        RecentCountVo vo = new RecentCountVo();
        RecentCount recentCount = new RecentCount();
        List<RecentCount.Vehicle> vehicles = new ArrayList<>();
        List<RecentCount.Amount> amounts = new ArrayList<>();

        DateTime date = DateUtil.date();
        DateTime end = DateUtil.endOfDay(date);
        DateTime begin = DateUtil.offsetMonth(DateUtil.beginOfMonth(end), -11);

        vo.setBeginTime(begin).setEndTime(end);

        List<RecentCountVo> vos = this.mapper.findByTime(vo);

        List<DateTime> dateTimes = DateUtil.rangeToList(begin, end, DateField.MONTH);//12个月日期
        for (DateTime dateTime : dateTimes) {
            RecentCount.Vehicle vehicle = new RecentCount.Vehicle();
            RecentCount.Amount amount = new RecentCount.Amount();
            String month = DateUtil.format(dateTime, "yyyy/MM");
            Long count = 0L;
            Long fee = 0L;
            for (RecentCountVo recentCountVo : vos) {
                if (recentCountVo.getMonth().equals(month)) {
                    count = recentCountVo.getCount();
                    fee = recentCountVo.getAmount();
                    break;
                }
            }
            vehicle.setCount(count).setMonth(month);
            amount.setAmount(fee).setMonth(month);
            vehicles.add(vehicle);
            amounts.add(amount);
        }
        recentCount.setRecent_vehicle(vehicles).setRecent_amount(amounts);
        return recentCount;
    }
}
