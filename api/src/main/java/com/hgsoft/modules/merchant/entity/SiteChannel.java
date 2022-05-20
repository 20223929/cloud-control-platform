package com.hgsoft.modules.merchant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * 交易场所与渠道中间表
 *
 * @author wujianwu
 * @date 2021/4/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "交易场所与渠道表", description = "交易场所与渠道表")
@TableName(value = "platform_site_channel", resultMap = "BaseResultMap")
public class SiteChannel {

    private static final long serialVersionUID = -2696964673874148631L;

    @TableId(value = "sys_id",type = IdType.AUTO)
    private Long sysId;

    /**
     * 场所编号
     */
    @TableField("mchExpSite_id")
    private String mchExpSiteId;

    /**
     * 接入渠道编号
     */
    @TableField("channel_id")
    private String channelId;

    /**
     * 状态，0-禁用；1-正常；
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;

    /**
     * 修改人
     */
    @TableField("updated_by")
    private String updatedBy;
    /**
     * 创建时间
     */
    @TableField("created_time")
    private Instant createdTime;

    /**
     * 修改时间
     */
    @TableField("updated_time")
    private Instant updatedTime;

    /**
     * 删除标识 0-删除；1-正常；
     */
    @TableField("del_flag")
    private Integer delFlag;

}
