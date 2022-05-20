package com.hgsoft.modules.merchant.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;

import com.hgsoft.ecip.framework.shiro.ShiroUser;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.BaseEntity;
import com.hgsoft.ecip.framework.core.entity.FlatTreeEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
/**
 * 商户信息 Entity
 * @author 吴锡霖
 * @version 2021-02-27 00:40:22
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="商户信息", description="商户信息")
@TableName(value = "tb_merchant", resultMap = "BaseResultMap")
public class TbMerchant extends FlatTreeEntity<TbMerchant> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TbMerchant() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@Length(min=1, max=64, message="id长度必须介于 1 和 64 之间")
	@Excel(name="id", width = 25)
    @TableId("id")
	@ApiModelProperty(value = "id")
	private String id;		// id

	@Length(min=1, max=150, message="名称长度必须介于 1 和 150 之间")
	@Excel(name="名称", width = 25)
    @TableField("name")
	@ApiModelProperty(value = "名称")
	private String name;		// 名称

	@Length(min=1, max=100, message="编号长度必须介于 1 和 100 之间")
	@Excel(name="编号", width = 25)
    @TableField("code")
	@ApiModelProperty(value = "编号")
	private String code;		// 编号

	@Excel(name="排序编号", width = 25)
    @TableField("sort")
	@ApiModelProperty(value = "排序编号")
	private Integer sort;		// 排序编号

	@Excel(name="节点level", width = 25)
    @TableField("node_level")
	@ApiModelProperty(value = "节点level")
	private Integer nodeLevel;		// 节点level

	@Length(min=0, max=2000, message="所有父级编号长度必须介于 0 和 2000 之间")
	@Excel(name="所有父级编号", width = 25)
    @TableField("parent_ids")
	@ApiModelProperty(value = "所有父级编号")
	private String parentIds;		// 所有父级编号

	@Length(min=0, max=64, message="父级编号长度必须介于 0 和 64 之间")
	@Excel(name="父级编号", width = 25)
    @TableField("parent_id")
	@ApiModelProperty(value = "父级编号")
	private String parentId;		// 父级编号

	@Excel(name="证件类型", width = 25)
    @TableField("id_card_type")
	@ApiModelProperty(value = "证件类型")
	private Integer idCardType;		// 证件类型

	@Length(min=0, max=30, message="证件号码长度必须介于 0 和 30 之间")
	@Excel(name="证件号码", width = 25)
    @TableField("id_card_num")
	@ApiModelProperty(value = "证件号码")
	private String idCardNum;		// 证件号码

	@Length(min=0, max=150, message="负责人长度必须介于 0 和 150 之间")
	@Excel(name="负责人", width = 25)
    @TableField("agentName")
	@ApiModelProperty(value = "负责人")
	private String agentName;		// 负责人

	@Length(min=0, max=11, message="手机号码长度必须介于 0 和 11 之间")
	@Excel(name="手机号码", width = 25)
    @TableField("mobile")
	@ApiModelProperty(value = "手机号码")
	private String mobile;		// 手机号码

	@Length(min=0, max=150, message="联系人长度必须介于 0 和 150 之间")
	@Excel(name="联系人", width = 25)
    @TableField("contact")
	@ApiModelProperty(value = "联系人")
	private String contact;		// 联系人

	@Length(min=0, max=20, message="联系方式长度必须介于 0 和 20 之间")
	@Excel(name="联系方式", width = 25)
    @TableField("tel")
	@ApiModelProperty(value = "联系方式")
	private String tel;		// 联系方式

	@Length(min=0, max=100, message="开户行银行编号长度必须介于 0 和 100 之间")
	@Excel(name="开户行银行编号", width = 25)
    @TableField("bank")
	@ApiModelProperty(value = "开户行银行编号")
	private String bank;		// 开户行银行编号

	@Length(min=0, max=100, message="开户银行支行长度必须介于 0 和 100 之间")
	@Excel(name="开户银行支行", width = 25)
    @TableField("bankBranch")
	@ApiModelProperty(value = "开户银行支行")
	private String bankBranch;		// 开户银行支行

	@Length(min=0, max=20, message="开户银行账户名长度必须介于 0 和 20 之间")
	@Excel(name="开户银行账户名", width = 25)
    @TableField("bank_account_name")
	@ApiModelProperty(value = "开户银行账户名")
	private String bankAccountName;		// 开户银行账户名

	@Length(min=0, max=100, message="开户银行账号长度必须介于 0 和 100 之间")
	@Excel(name="开户银行账号", width = 25)
    @TableField("bank_account")
	@ApiModelProperty(value = "开户银行账号")
	private String bankAccount;		// 开户银行账号

	@Excel(name="结算周期，单位天", width = 25)
    @TableField("settle_period")
	@ApiModelProperty(value = "结算周期，单位天")
	private Integer settlePeriod;		// 结算周期，单位天

	@Excel(name="清分服务费率", width = 25)
    @TableField("clear_service_rate")
	@ApiModelProperty(value = "清分服务费率")
	private Double clearServiceRate;		// 清分服务费率

	@Length(min=0, max=300, message="地址长度必须介于 0 和 300 之间")
	@Excel(name="地址", width = 25)
    @TableField("address")
	@ApiModelProperty(value = "地址")
	private String address;		// 地址

	@Length(min=0, max=30, message="经度长度必须介于 0 和 30 之间")
	@Excel(name="经度", width = 25)
    @TableField("lat")
	@ApiModelProperty(value = "经度")
	private String lat;		// 经度

	@Length(min=0, max=30, message="纬度长度必须介于 0 和 30 之间")
	@Excel(name="纬度", width = 25)
    @TableField("lng")
	@ApiModelProperty(value = "纬度")
	private String lng;		// 纬度

	@Length(min=0, max=6, message="省份编码长度必须介于 0 和 6 之间")
	@Excel(name="省份编码", width = 25)
    @TableField("province_num")
	@ApiModelProperty(value = "省份编码")
	private String provinceNum;		// 省份编码

	@Length(min=0, max=10, message="起始日期长度必须介于 0 和 10 之间")
	@Excel(name="起始日期", width = 25)
    @TableField("start_time")
	@ApiModelProperty(value = "起始日期")
	private String startTime;		// 起始日期

	@Length(min=0, max=10, message="结束日期长度必须介于 0 和 10 之间")
	@Excel(name="结束日期", width = 25)
    @TableField("end_time")
	@ApiModelProperty(value = "结束日期")
	private String endTime;		// 结束日期

	@Length(min=0, max=15, message="所属运营方编号长度必须介于 0 和 15 之间")
	@Excel(name="所属运营方编号", width = 25)
    @TableField("operator_id")
	@ApiModelProperty(value = "所属运营方编号")
	private String operatorId;		// 所属运营方编号

	@Excel(name="清分服务类型  0按金额，1按条数", width = 25)
    @TableField("clear_service_type")
	@ApiModelProperty(value = "清分服务类型  0按金额，1按条数")
	private Integer clearServiceType;		// 清分服务类型  0按金额，1按条数

	@Excel(name="银联划账手续费类型  0按金额，1按条数", width = 25)
    @TableField("union_service_type")
	@ApiModelProperty(value = "银联划账手续费类型  0按金额，1按条数")
	private Integer unionServiceType;		// 银联划账手续费类型  0按金额，1按条数

	@Excel(name="划账手续费率/（划账手续费/条）", width = 25)
    @TableField("union_service_rate")
	@ApiModelProperty(value = "划账手续费率/（划账手续费/条）")
	private Double unionServiceRate;		// 划账手续费率/（划账手续费/条）

	@Length(min=0, max=300, message="开户行地址长度必须介于 0 和 300 之间")
	@Excel(name="开户行地址", width = 25)
    @TableField("bank_address")
	@ApiModelProperty(value = "开户行地址")
	private String bankAdress;		// 开户行地址

	@Length(min=0, max=255, message="商户类型，2：停车场，3：加油站，4：服务区，5：市政拓展，6：充电桩长度必须介于 0 和 255 之间")
	@Excel(name="商户类型，2：停车场，3：加油站，4：服务区，5：市政拓展，6：充电桩", width = 25)
    @TableField("merchant_type")
	@ApiModelProperty(value = "商户类型，2：停车场，3：加油站，4：服务区，5：市政拓展，6：充电桩")
	private String merchantType;		// 商户类型，1：停车场，2：服务区，3：加油站

	@Excel(name="接入类型，0-联网中心；1-银联", width = 25)
    @TableField("access_type")
	@ApiModelProperty(value = "接入类型，0-联网中心；1-银联")
	private Integer accessType;		// 接入类型，0-联网中心；1-银联

	@Excel(name="入口数", width = 25)
    @TableField("entrance_num")
	@ApiModelProperty(value = "入口数")
	private Integer entranceNum;		// 入口数

	@Excel(name="出口数", width = 25)
    @TableField("exit_num")
	@ApiModelProperty(value = "出口数")
	private Integer exitNum;		// 出口数

	@Excel(name="车位数", width = 25)
    @TableField("parking_space_num")
	@ApiModelProperty(value = "车位数")
	private Integer parkingSpaceNum;		// 车位数

	@Excel(name="加油枪数量", width = 25)
    @TableField("compressor_gun_num")
	@ApiModelProperty(value = "加油枪数量")
	private Integer compressorGunNum;		// 加油枪数量

	@Length(min=0, max=32, message="银行服务商编号长度必须介于 0 和 32 之间")
	@Excel(name="银行服务商编号", width = 25)
    @TableField("mer_group_no")
	@ApiModelProperty(value = "银行服务商编号")
	private String merGroupNo;		// 银行服务商编号

	@Length(min=0, max=32, message="银行商户编号长度必须介于 0 和 32 之间")
	@Excel(name="银行商户编号", width = 25)
    @TableField("merchant_no")
	@ApiModelProperty(value = "银行商户编号")
	private String merchantNo;		// 银行商户编号

	@Length(min=0, max=32, message="银行商户协议号长度必须介于 0 和 32 之间")
	@Excel(name="银行商户协议号", width = 25)
    @TableField("merPrtcl_no")
	@ApiModelProperty(value = "银行商户协议号")
	private String merPrtclNo;		// 银行商户协议号

	@Excel(name="银行商户启用或禁用：0-启用；1-禁用；", width = 25)
    @TableField("enabled")
	@ApiModelProperty(value = "银行商户启用或禁用：0-启用；1-禁用；")
	private Integer enabled;		// 银行商户启用或禁用：0-启用；1-禁用；

	@Length(min=0, max=50, message="银行商户交易场所编号长度必须介于 0 和 50 之间")
	@Excel(name="银行商户交易场所编号", width = 25)
    @TableField("trx_place")
	@ApiModelProperty(value = "银行商户交易场所编号")
	private String trxPlace;		// 银行商户交易场所编号

	@Excel(name="银行手续费", width = 25)
    @TableField("bank_rate")
	@ApiModelProperty(value = "银行手续费")
	private Double bankRate;		// 银行手续费

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
	@Excel(name="终端机编号", width = 25)
	@TableField(value = "terminal_no", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "终端机编号")
	private String terminalNo;


}