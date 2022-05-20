package com.hgsoft.modules.merchant.mapper;

import com.hgsoft.modules.merchant.entity.Channel;
import com.hgsoft.modules.merchant.entity.SiteChannel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 交易场所与接入渠道mapper
 * Created by 吴鉴武 on 2022/1/10 16:53
 */
@Repository("com.hgsoft.modules.merchant.mapper.SiteChannelMapper")
public interface SiteChannelMapper {

    /**
     * 插入交易场所与渠道信息
     *
     * @param siteChannel
     */
    void insert(@Param("entity") SiteChannel siteChannel);

}
