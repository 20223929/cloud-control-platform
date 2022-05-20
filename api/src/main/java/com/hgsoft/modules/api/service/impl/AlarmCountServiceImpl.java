package com.hgsoft.modules.api.service.impl;

import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.modules.api.entity.AlarmCount;
import com.hgsoft.modules.api.entity.AlarmCountVo;
import com.hgsoft.modules.api.mapper.AlarmCountMapper;
import com.hgsoft.modules.api.service.AlarmCountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("com.hgsoft.modules.api.service.AlarmCountService")
public class AlarmCountServiceImpl extends CrudServiceImpl<AlarmCountMapper, AlarmCountVo> implements AlarmCountService {

    @Override
    public AlarmCount findAlarmCount() {
        AlarmCount alarmCount = new AlarmCount();
        Integer generalCount = 0;
        Integer seriousCount = 0;
        List<AlarmCountVo> vos = this.mapper.findAlarmCount();

        List<AlarmCountVo> generals = vos.stream().filter(c -> c.getAlarmLevel() == 1).collect(Collectors.toList());
        List<AlarmCountVo> seriouss = vos.stream().filter(c -> c.getAlarmLevel() == 2).collect(Collectors.toList());
        if (!generals.isEmpty()) {
            generalCount = generals.get(0).getCount();
        }
        if (!seriouss.isEmpty()) {
            seriousCount = seriouss.get(0).getCount();
        }
        alarmCount.setSerious_alarm_count(seriousCount).setGeneral_alarm_count(generalCount);

        //ETC全量黑名单
        AlarmCountVo fullCardVersion = this.mapper.findVersion(new AlarmCountVo().setBlackType(1).setPackageType(0));
        if (fullCardVersion != null) {
            alarmCount.setFull_card_blacklist_version(fullCardVersion.getVersion());
        }
        //ETC增量黑名单
        AlarmCountVo incrCardVersion = this.mapper.findVersion(new AlarmCountVo().setBlackType(1).setPackageType(1));
        if (incrCardVersion != null) {
            alarmCount.setIncre_card_blacklist_version(incrCardVersion.getVersion());
        }
        //OBU全量黑名单
        AlarmCountVo fullObuVersion = this.mapper.findVersion(new AlarmCountVo().setBlackType(2).setPackageType(0));
        if (fullObuVersion != null) {
            alarmCount.setFull_obu_blacklist_version(fullObuVersion.getVersion());
        }
        //OBU增量黑名单
        AlarmCountVo incrObuVersion = this.mapper.findVersion(new AlarmCountVo().setBlackType(2).setPackageType(1));
        if (incrObuVersion != null) {
            alarmCount.setIncre_obu_blacklist_version(incrObuVersion.getVersion());
        }

        return alarmCount;
    }
}
