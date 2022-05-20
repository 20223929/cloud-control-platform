package com.hgsoft.modules.merchant.mapper;

import com.hgsoft.modules.merchant.entity.Channel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 接入渠道mapper
 * Created by 吴鉴武 on 2022/1/10 16:53
 */
@Repository("com.hgsoft.modules.merchant.mapper.ChannelMapper")
public interface ChannelMapper {

    /**
     * 插入渠道信息
     *
     * @param channel
     */
    void insert(@Param("entity") Channel channel);

}
