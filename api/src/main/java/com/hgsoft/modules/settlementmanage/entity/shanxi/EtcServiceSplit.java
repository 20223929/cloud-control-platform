package com.hgsoft.modules.settlementmanage.entity.shanxi;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 联网中心服务费拆分结算管理 Entity
 * @author 吴鉴武
 * @version 2021-04-25 01:49:40
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="联网中心服务费拆分结算管理", description="联网中心服务费拆分结算管理")
@TableName(value = "tb_etc_service_split", resultMap = "BaseResultMap")
public class EtcServiceSplit extends DataEntity<EtcServiceSplit> implements Serializable {

    private static final long serialVersionUID = 1L;

    public EtcServiceSplit() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@Length(min=1, max=64, message="交易日期长度必须介于 1 和 64 之间")
    @Excel(name="交易日期", width = 25)
    @TableField("trade_day")
	@ApiModelProperty(value = "交易日期")
	private String tradeDay;		// 交易日期

	@NotNull(message="总服务费不能为空")
    @Excel(name="总服务费", width = 25)
    @TableField("total_service_fee")
	@ApiModelProperty(value = "总服务费")
	private BigDecimal totalServiceFee;		// 总服务费

	@Length(min=1, max=64, message="渠道方长度必须介于 1 和 64 之间")
    @Excel(name="渠道方", width = 25)
    @TableField("channel_name")
	@ApiModelProperty(value = "渠道方")
	private String channelName;		// 渠道方

	@NotNull(message="渠道方服务费不能为空")
    @Excel(name="渠道方服务费", width = 25)
    @TableField("channel_service_fee")
	@ApiModelProperty(value = "渠道方服务费")
	private BigDecimal channelServiceFee;		// 渠道方服务费

	@Length(min=1, max=64, message="渠道方服务费率长度必须介于 1 和 64 之间")
    @Excel(name="渠道方服务费率", width = 25)
    @TableField("channel_service_rate")
	@ApiModelProperty(value = "渠道方服务费率")
	private String channelServiceRate;		// 渠道方服务费率

	@Length(min=1, max=64, message="平台长度必须介于 1 和 64 之间")
    @Excel(name="平台", width = 25)
    @TableField("platform")
	@ApiModelProperty(value = "平台")
	private String platform;		// 平台

	@NotNull(message="平台运营成本不能为空")
    @Excel(name="平台运营成本", width = 25)
    @TableField("platform_operation_cost")
	@ApiModelProperty(value = "平台运营成本")
	private BigDecimal platformOperationCost;		// 平台运营成本

	@Length(min=1, max=64, message="平台运营服务费率长度必须介于 1 和 64 之间")
    @Excel(name="平台运营服务费率", width = 25)
    @TableField("platform_service_rate")
	@ApiModelProperty(value = "平台运营服务费率")
	private String platformServiceRate;		// 平台运营服务费率

	@Length(min=1, max=64, message="拓展方长度必须介于 1 和 64 之间")
    @Excel(name="拓展方", width = 25)
    @TableField("merchant_group_name")
	@ApiModelProperty(value = "拓展方")
	private String merchantGroupName;		// 拓展方

	@NotNull(message="拓展方服务费不能为空")
    @Excel(name="拓展方服务费", width = 25)
    @TableField("merchant_group_service_fee")
	@ApiModelProperty(value = "拓展方服务费")
	private BigDecimal merchantGroupServiceFee;		// 拓展方服务费

	@Length(min=1, max=64, message="拓拓展方服务费率长度必须介于 1 和 64 之间")
    @Excel(name="拓拓展方服务费率", width = 25)
    @TableField("merchant_group_service_rate")
	@ApiModelProperty(value = "拓拓展方服务费率")
	private String merchantGroupServiceRate;		// 拓拓展方服务费率

	@NotNull(message="核对状态不能为空")
    @Excel(name="核对状态", width = 25)
    @TableField("check_status")
	@ApiModelProperty(value = "核对状态")
	private Integer checkStatus;		// 核对状态

    @Excel(name="操作时间", width = 25)
    @TableField("operation_time")
	@ApiModelProperty(value = "操作时间")
	private String operationTime;		// 操作时间

}