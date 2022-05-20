package com.hgsoft.modules.merchant.entity;

import org.hibernate.validator.constraints.Length;
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

import java.util.List;
import java.util.ArrayList;


import java.io.Serializable;
/**
 * 用户商户关联表 Entity
 * @author 吴锡霖
 * @version 2021-04-19 09:13:54
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="用户商户关联表", description="用户商户关联表")
@TableName(value = "sys_user_merchant", resultMap = "BaseResultMap")
public class SysUserMerchant extends DataEntity<SysUserMerchant> implements Serializable {

    private static final long serialVersionUID = 1L;

    public SysUserMerchant() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@Length(min=1, max=64, message="主键长度必须介于 1 和 64 之间")
    @Excel(name="主键", width = 25)
    @TableId("id")
	@ApiModelProperty(value = "主键")
	private String id;		// 主键

	@Length(min=1, max=64, message="用户id长度必须介于 1 和 64 之间")
    @Excel(name="用户id", width = 25)
    @TableField("user_id")
	@ApiModelProperty(value = "用户id")
	private String userId;		// 用户id

	@Length(min=1, max=64, message="商户id长度必须介于 1 和 64 之间")
    @Excel(name="商户id", width = 25)
    @TableField("merchant_id")
	@ApiModelProperty(value = "商户id")
	private String merchantId;		// 商户id

    @TableField(value = "create_by", el = "createBy.id", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建者")
	private ShiroUser createBy;		// 创建者

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private Date createDate;		// 创建时间

    @TableField(value = "update_by", el = "updateBy.id", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "更新者")
	private ShiroUser updateBy;		// 更新者

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "更新时间")
	private Date updateDate;		// 更新时间

	@Length(min=0, max=255, message="备注信息长度必须介于 0 和 255 之间")
    @Excel(name="备注信息", width = 25)
    @TableField(value = "remarks", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "备注信息")
	private String remarks;		// 备注信息

	@Length(min=0, max=64, message="逻辑删除标记（0：显示；1：隐藏）长度必须介于 0 和 64 之间")
    @TableField(value = "del_flag", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "逻辑删除标记（0：显示；1：隐藏）")
	private String delFlag;		// 逻辑删除标记（0：显示；1：隐藏）

}
