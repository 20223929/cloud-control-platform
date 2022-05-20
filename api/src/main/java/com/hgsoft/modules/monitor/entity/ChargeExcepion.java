package com.hgsoft.modules.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 收费异常管理 Entity
 * @author 吴鉴武
 * @version 2021-04-21 22:35:10
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ETC交易合计数", description="收费异常管理")
@TableName(value = "tb_etc_static", resultMap = "BaseResultMap")
public class ChargeExcepion extends DataEntity<ChargeExcepion> implements Serializable {

    private static final long serialVersionUID = 1L;

    public ChargeExcepion() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

    private String[] timeScope;

	@Length(min=1, max=10, message="脱机交易日，格式：yyyy-MM-dd长度必须介于 1 和 10 之间")
    @Excel(name="交易日期", width = 25)
    @TableId("trans_date")
	@ApiModelProperty(value = "脱机交易日，格式：yyyy-MM-dd")
	private String transDate;		// 脱机交易日，格式：yyyy-MM-dd

	@Length(min=1, max=60, message="一级运营商长度必须介于 1 和 60 之间")
    @TableId("merchant_group_id")
	@ApiModelProperty(value = "一级运营商")
	private String merchantGroupId;		// 一级运营商
	@Excel(name="拓展方", width = 25)
	private String merchantGroupName;

	@Length(min=1, max=60, message="二级运营商长度必须介于 1 和 60 之间")
    @TableId("merchant_id")
	@ApiModelProperty(value = "二级运营商")
	private String merchantId;		// 二级运营商
	@Excel(name="运营方", width = 25)
	private String merchantName;

	@Length(min=1, max=60, message="三级运营商长度必须介于 1 和 60 之间")
    @TableId("site_id")
	@ApiModelProperty(value = "三级运营商")
	private String siteId;		// 三级运营商
	@Excel(name="场景", width = 25)
	private String siteName;

	@Length(min=1, max=60, message="四级设备编号长度必须介于 1 和 60 之间")
    @Excel(name="设备编号", width = 25)
    @TableId("equipment_id")
	@ApiModelProperty(value = "四级设备编号")
	private String equipmentId;		// 四级设备编号

	@NotNull(message="前端脱机流水总数不能为空")
    @Excel(name="前端脱机流水总数", width = 25)
    @TableField("etc_count")
	@ApiModelProperty(value = "前端脱机流水总数")
	private Long etcCount;		// 前端脱机流水总数

	@NotNull(message="后台脱机流水总数不能为空")
    @Excel(name="后台脱机流水总数", width = 25)
    @TableField("real_etc_count")
	@ApiModelProperty(value = "后台脱机流水总数")
	private Long realEtcCount;		// 后台脱机流水总数

	@NotNull(message="前端脱机实收总金额不能为空")
	@Excel(name="前端脱机实收总金额(元)", width = 25)
	@TableField("etc_amount")
	@ApiModelProperty(value = "前端脱机实收总金额")
	private BigDecimal etcAmount;		// 前端脱机实收总金额

	@NotNull(message="后台脱机实收总金额不能为空")
    @Excel(name="后台脱机实收总金额(元)", width = 25)
    @TableField("real_etc_amount")
	@ApiModelProperty(value = "后台脱机实收总金额")
	private BigDecimal realEtcAmount;		// 后台脱机实收总金额

	@NotNull(message="前端联机流水总数不能为空")
    @Excel(name="前端联机流水总数", width = 25)
    @TableField("online_count")
	@ApiModelProperty(value = "前端联机流水总数")
	private Long onlineCount;		// 前端联机流水总数

	@NotNull(message="后台联机流水总数不能为空")
    @Excel(name="后台联机流水总数", width = 25)
    @TableField("real_online_count")
	@ApiModelProperty(value = "后台联机流水总数")
	private Long realOnlineCount;		// 后台联机流水总数

	@NotNull(message="前端联机实收总金额不能为空")
	@Excel(name="前端联机实收总金额(元)", width = 25)
	@TableField("online_amount")
	@ApiModelProperty(value = "前端联机实收总金额")
	private BigDecimal onlineAmount;		// 前端联机实收总金额

	@NotNull(message="后台联机实收总金额不能为空")
    @Excel(name="后台联机实收总金额(元)", width = 25)
    @TableField("real_online_amount")
	@ApiModelProperty(value = "后台联机实收总金额")
	private BigDecimal realOnlineAmount;		// 后台联机实收总金额

	@NotNull(message="前端流水总数不能为空")
    @Excel(name="前端流水总数", width = 25)
    @TableField("total_etc_count")
	@ApiModelProperty(value = "前端流水总数")
	private Long totalEtcCount;		// 前端流水总数

	@NotNull(message="后台流水总数不能为空")
	@Excel(name="后台流水总数", width = 25)
	@TableField("total_online_count")
	@ApiModelProperty(value = "后台流水总数")
	private Long totalOnlineCount;		// 后台流水总数

	@NotNull(message="前端实收总金额不能为空")
    @Excel(name="前端实收总金额(元)", width = 25)
    @TableField("total_etc_amount")
	@ApiModelProperty(value = "前端实收总金额")
	private BigDecimal totalEtcAmount;		// 前端实收总金额

	@NotNull(message="后台实收总金额不能为空")
    @Excel(name="后台实收总金额(元)", width = 25)
    @TableField("total_online_amount")
	@ApiModelProperty(value = "后台实收总金额")
	private BigDecimal totalOnlineAmount;		// 后台实收总金额

	@Length(min=1, max=64, message="状态长度必须介于 1 和 64 之间")
    @Excel(name="状态", width = 25)
    @TableField("status")
	@ApiModelProperty(value = "状态")
	private String status;		// 状态

	@NotNull(message="确认时间不能为空")
//    @Excel(name="确认时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("confirm_time")
	@ApiModelProperty(value = "确认时间")
	private Date confirmTime;		// 确认时间

	@Length(min=1, max=50, message="确认人长度必须介于 1 和 50 之间")
//    @Excel(name="确认人", width = 25)
    @TableField("confirm_man")
	@ApiModelProperty(value = "确认人")
	private String confirmMan;		// 确认人

	@Length(min=1, max=800, message="批注长度必须介于 1 和 800 之间")
//    @Excel(name="批注", width = 25)
    @TableField("remark")
	@ApiModelProperty(value = "批注")
	private String remark;		// 批注

	/**
	 * 用户商户权限字段
	 */
	@TableField(exist = false)
	private UserMerchantPermissionswrapper.UserMerchantParam userMerchantParam;

}