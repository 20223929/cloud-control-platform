package com.hgsoft.modules.monitor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.modules.monitor.entity.BlacklistVersion;
import com.hgsoft.modules.monitor.mapper.BlacklistVersionMapper;
import com.hgsoft.modules.monitor.service.BlacklistVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 黑名单版本监控ServiceImpl
 * @author 吴鉴武
 * @version 2021-04-21 02:24:32
 */

@Service("com.hgsoft.modules.monitor.service.BlacklistVersionService")
@Slf4j
public class BlacklistVersionServiceImpl extends CrudServiceImpl<BlacklistVersionMapper, BlacklistVersion> implements BlacklistVersionService {


    @Override
    public List<BlacklistVersion> getBlacklistVersion() {
        List<BlacklistVersion> list = this.baseMapper.getBlacklistVersion();
        if(CollUtil.isEmpty(list)) return new ArrayList<>();
        BlacklistVersion blacklistVersion = list.stream().reduce(new BlacklistVersion(),(v1,v2)->{
            BeanUtil.copyProperties(v2,v1,CopyOptions.create().ignoreNullValue());
            return v1;
        });
        return Arrays.asList(blacklistVersion);
    }
}