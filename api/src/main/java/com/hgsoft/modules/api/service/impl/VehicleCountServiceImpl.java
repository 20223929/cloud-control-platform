package com.hgsoft.modules.api.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.modules.api.entity.VehicleCount;
import com.hgsoft.modules.api.entity.VehicleCountVo;
import com.hgsoft.modules.api.mapper.VehicleCountMapper;
import com.hgsoft.modules.api.service.VehicleCountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service("com.hgsoft.modules.api.service.VehicleCountService")
public class VehicleCountServiceImpl extends CrudServiceImpl<VehicleCountMapper, VehicleCountVo> implements VehicleCountService {

    /**
     * 过车交易统计接口
     *
     * @param vo
     * @return
     */
    @Override
    public VehicleCount vehicleCount(VehicleCountVo vo) {
        VehicleCount vehicleCount = new VehicleCount();
        List<VehicleCountVo> voList = this.mapper.findByTime(vo);
        if (voList.isEmpty()) {
            return vehicleCount;
        }
        long parkFee = 0;
        long parkTotal = 0;
        long gasFee = 0;
        long gasTotal = 0;
        List<VehicleCountVo> parkList = voList.stream().filter(c -> c.getServiceType() == 2).collect(Collectors.toList());
        List<VehicleCountVo> gasList = voList.stream().filter(c -> c.getServiceType() == 3).collect(Collectors.toList());
        if (!parkList.isEmpty()) {
            parkFee = parkList.stream().mapToLong(VehicleCountVo::getFee).sum();
            parkTotal = parkList.stream().mapToLong(VehicleCountVo::getTotal).sum();
        }
        if (!gasList.isEmpty()) {
            gasFee = gasList.stream().mapToLong(VehicleCountVo::getFee).sum();
            gasTotal = gasList.stream().mapToLong(VehicleCountVo::getTotal).sum();
        }
        long sumFee = parkFee + gasFee;
        long sumTotal = parkTotal + gasTotal;

        BigDecimal amount_parking_lot_ratio = NumberUtil.round((double)parkFee/sumFee, 4);
        BigDecimal amount_gas_station_ratio = NumberUtil.round((double)gasFee/sumFee, 4);
        BigDecimal vehicle_parking_lot_ratio = NumberUtil.round((double)parkTotal/sumTotal, 4);
        BigDecimal vehicle_gas_station_ratio = NumberUtil.round((double)gasTotal/sumTotal, 4);

        vehicleCount.setVehicle_total(sumTotal);
        vehicleCount.setAmount_total(sumFee);
        vehicleCount.setVehicle_gas_station_ratio(NumberUtil.decimalFormat("#.##%", vehicle_gas_station_ratio));
        vehicleCount.setVehicle_parking_lot_ratio(NumberUtil.decimalFormat("#.##%", vehicle_parking_lot_ratio));
        vehicleCount.setAmount_gas_station_ratio(NumberUtil.decimalFormat("#.##%", amount_gas_station_ratio));
        vehicleCount.setAmount_parking_lot_ratio(NumberUtil.decimalFormat("#.##%", amount_parking_lot_ratio));

        return vehicleCount;
    }
}
