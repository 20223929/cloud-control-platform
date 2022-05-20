package com.hgsoft.modules.api.service.impl;

import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.modules.api.entity.TodayRank;
import com.hgsoft.modules.api.entity.TodayRankVo;
import com.hgsoft.modules.api.mapper.TodayRankMapper;
import com.hgsoft.modules.api.service.TodayRankService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("com.hgsoft.modules.api.service.TodayRankService")
public class TodayRankServiceImpl extends CrudServiceImpl<TodayRankMapper, TodayRankVo> implements TodayRankService {

    @Override
    public TodayRank findTodayRank(TodayRankVo vo) {
        TodayRank rank = new TodayRank();
        List<TodayRank.GasStation> gasStations = new ArrayList<>();
        List<TodayRank.ParkLot> parkLots = new ArrayList<>();

        //2-停车场、3-加油站 4-服务区 5-市政拓展 6-充电桩
        vo.setServiceType(3);
        List<TodayRankVo> rankVos = this.mapper.findByTime(vo);
        rankVos.listIterator().forEachRemaining(c -> {
            TodayRank.GasStation gasStation = new TodayRank.GasStation();
            gasStation.setName(c.getName()).setAmount(c.getFee());
            gasStations.add(gasStation);
        });

        vo.setServiceType(2);
        if (!rankVos.isEmpty()) rankVos.clear();
        rankVos = this.mapper.findByTime(vo);

        rankVos.listIterator().forEachRemaining(c -> {
            TodayRank.ParkLot parkLot = new TodayRank.ParkLot();
            parkLot.setName(c.getName()).setAmount(c.getFee());
            parkLots.add(parkLot);
        });

//        if (!parkLots.isEmpty()) rank.setParking_lot_rank(parkLots);
//        if (!gasStations.isEmpty()) rank.setGas_station_rank(gasStations);
        rank.setParking_lot_rank(parkLots).setGas_station_rank(gasStations);

        return rank;
    }
}
