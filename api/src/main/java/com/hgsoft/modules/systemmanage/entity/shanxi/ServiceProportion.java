package com.hgsoft.modules.systemmanage.entity.shanxi;

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
/**
 * 服务费分成信息管理 Entity
 * @author 吴鉴武
 * @version 2021-04-25 02:49:29
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="服务费分成信息管理", description="服务费分成信息管理")
@TableName(value = "tb_service_proportion", resultMap = "BaseResultMap")
public class ServiceProportion extends DataEntity<ServiceProportion> implements Serializable {

    private static final long serialVersionUID = 1L;

    public ServiceProportion() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@Length(min=1, max=64, message="拓展方编号长度必须介于 1 和 64 之间")
    @Excel(name="拓展方编号", width = 25)
    @TableField("merchant_group_id")
	@ApiModelProperty(value = "拓展方编号")
	private String merchantGroupId;		// 拓展方编号

	@Length(min=1, max=64, message="运营方编号长度必须介于 1 和 64 之间")
    @Excel(name="运营方编号", width = 25)
    @TableField("merchant_id")
	@ApiModelProperty(value = "运营方编号")
	private String merchantId;		// 运营方编号

	@Length(min=1, max=64, message="场景编号长度必须介于 1 和 64 之间")
    @Excel(name="场景编号", width = 25)
    @TableField("site_id")
	@ApiModelProperty(value = "场景编号")
	private String siteId;		// 场景编号

	@Length(min=1, max=64, message="渠道方分成比例长度必须介于 1 和 64 之间")
    @Excel(name="渠道方分成比例", width = 25)
    @TableField("channel_proportion")
	@ApiModelProperty(value = "渠道方分成比例")
	private String channelProportion;		// 渠道方分成比例

	@Length(min=1, max=64, message="平台方分成比例长度必须介于 1 和 64 之间")
    @Excel(name="平台方分成比例", width = 25)
    @TableField("platform_proportion")
	@ApiModelProperty(value = "平台方分成比例")
	private String platformProportion;		// 平台方分成比例

	@Length(min=1, max=64, message="拓展方分成比例长度必须介于 1 和 64 之间")
    @Excel(name="拓展方分成比例", width = 25)
    @TableField("merchant_group_proportion")
	@ApiModelProperty(value = "拓展方分成比例")
	private String merchantGroupProportion;		// 拓展方分成比例

	@Length(min=1, max=64, message="版本号长度必须介于 1 和 64 之间")
    @Excel(name="版本号", width = 25)
    @TableField("version")
	@ApiModelProperty(value = "版本号")
	private String version;		// 版本号

    @Excel(name="开始时间", width = 25)
    @TableField("start_time")
	@ApiModelProperty(value = "开始时间")
	private String startTime;		// 开始时间

    @Excel(name="结束时间", width = 25)
    @TableField("end_time")
	@ApiModelProperty(value = "结束时间")
	private String endTime;		// 结束时间

	@Length(min=1, max=64, message="登记人长度必须介于 1 和 64 之间")
    @Excel(name="登记人", width = 25)
    @TableField("registrant")
	@ApiModelProperty(value = "登记人")
	private String registrant;		// 登记人

    @Excel(name="登记时间", width = 25)
    @TableField("register_time")
	@ApiModelProperty(value = "登记时间")
	private String registerTime;		// 登记时间

	@NotNull(message="状态不能为空")
    @Excel(name="状态", width = 25)
    @TableField("status")
	@ApiModelProperty(value = "状态")
	private Integer status;		// 状态

}