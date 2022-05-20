package com.hgsoft.modules.monitor.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;

import com.hgsoft.ecip.framework.shiro.ShiroUser;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.io.Serializable;
/**
 * 异常报警管理 Entity
 * @author 雷新辉
 * @version 2021-07-06 03:40:21
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="异常报警表", description="异常报警管理")
@TableName(value = "tb_abnormal_alarm", resultMap = "BaseResultMap")
public class TbAbnormalAlarm extends DataEntity<TbAbnormalAlarm> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TbAbnormalAlarm() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

    @TableId("sys_id")
	@ApiModelProperty(value = "系统编号")
	private String sysId;		// 系统编号

	@Length(min=1, max=11, message="报警类型，数据字典配置，1-黑名单异常、2-对账异常、3-收费异常、4-服务器异常、5-网络异常、6-应用异常、7-车道监控异常长度必须介于 1 和 11 之间")
    @TableField("alarm_type")
	@ApiModelProperty(value = "报警类型")
	private Integer alarmType;		// 报警类型，数据字典配置，1-黑名单异常、2-对账异常、3-收费异常、4-服务器异常、5-网络异常、6-应用异常、7-车道监控异常

	@Excel(name="报警类型", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "报警类型")
	private String alarmTypeName;

	@NotNull(message="报警时间不能为空")
    @Excel(name="报警时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("alarm_time")
	@ApiModelProperty(value = "报警时间")
	private Date alarmTime;		// 报警时间

	@NotNull(message="报警级别，1-一般、2-严重不能为空")
    @TableField("alarm_level")
	@ApiModelProperty(value = "报警级别")
	private Integer alarmLevel;		// 报警级别，1-一般、2-严重\

	@Excel(name="报警级别", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "报警级别")
	private String alarmLevelName;		// 报警级别，1-一般、2-严重

	@Length(min=1, max=1000, message="报警信息长度必须介于 1 和 1000 之间")
    @Excel(name="报警信息", width = 25)
    @TableField("alarm_info")
	@ApiModelProperty(value = "报警信息")
	private String alarmInfo;		// 报警信息

	@NotNull(message="处理状态，0-未处理、1-已处理不能为空")
    @TableField("deal_state")
	@ApiModelProperty(value = "处理状态")
	private Integer dealState;		// 处理状态，0-未处理、1-已处理

	@Excel(name="处理状态", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "处理状态")
	private String dealStateName;		// 处理状态，0-未处理、1-已处理

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("deal_time")
	@ApiModelProperty(value = "处理时间")
	private Date dealTime;		// 处理时间

	@Length(min=0, max=32, message="处理人id长度必须介于 0 和 32 之间")
    @TableField("deal_user_id")
	@ApiModelProperty(value = "处理人id")
	private String dealUserId;		// 处理人id

	@NotNull(message="创建时间不能为空")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;		// 创建时间

	@NotNull(message="更新时间不能为空")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;		// 更新时间

	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date beginAlarmTime;		// 开始 报警时间

	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endAlarmTime;		// 结束 报警时间

	private String[] timeScope;
}