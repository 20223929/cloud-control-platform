package com.hgsoft.modules.merchant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.Instant;

/**
 * 接入渠道
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "接入渠道表", description = "接入渠道表")
@TableName(value = "platform_channel", resultMap = "BaseResultMap")
public class Channel implements Serializable {

    private static final long serialVersionUID = -6438136172880539120L;
    @TableId(value = "sys_id", type = IdType.AUTO)
    private Long sysId;

    /**
     * 接入渠道名称
     */
    @TableField("channel_name")
    private String channelName;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Instant createdTime;

    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;

    /**
     * 删除标识：0-删除；1-正常；
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 接入渠道编号
     */
    @TableField("channel_id")
    private String channelId;

    /**
     * 状态: 0-禁用; 1-启用
     */
    @TableField("status")
    private Integer status;
}
