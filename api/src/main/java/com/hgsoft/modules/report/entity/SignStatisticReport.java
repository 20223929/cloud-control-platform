package com.hgsoft.modules.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 二次签约统计报表 Entity
 * @author 吴鉴武
 * @version 2021-04-20 22:01:36
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="二次签约统计报表", description="二次签约统计报表")
@TableName(value = "tb_sign_statistic_report", resultMap = "BaseResultMap")
public class SignStatisticReport extends DataEntity<SignStatisticReport> implements Serializable {

    private static final long serialVersionUID = 1L;

    public SignStatisticReport() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	/**
	 * 查询使用（统计日期范围）
	 */
	private String[] sumDateScope;

	@NotNull(message="系统编号不能为空")
    @TableId("sys_id")
	@ApiModelProperty(value = "系统编号")
	private Long sysId;		// 系统编号

	@Length(min=1, max=10, message="统计日期长度必须介于 1 和 10 之间")
    @Excel(name="日期", width = 25)
    @TableField("statistics_date")
	@ApiModelProperty(value = "统计日期")
	private String statisticsDate;		// 统计日期

	@Length(min=1, max=10, message="签约机构长度必须介于 1 和 10 之间")
    @Excel(name="签约机构", width = 25)
    @TableField("sign_institution")
	@ApiModelProperty(value = "签约机构")
	private String signInstitution;		// 签约机构

	@Length(min=1, max=20, message="签约银行长度必须介于 1 和 20 之间")
    @Excel(name="发卡行", width = 25)
    @TableField("sign_bank")
	@ApiModelProperty(value = "签约银行")
	private String signBank;		// 签约银行

	@NotNull(message="新增签约用户数不能为空")
    @Excel(name="新增签约用户数", width = 25)
    @TableField("increase_count")
	@ApiModelProperty(value = "新增签约用户数")
	private Long increaseCount;		// 新增签约用户数

	@NotNull(message="流水总记录数不能为空")
    @Excel(name="签约总用户数", width = 25)
    @TableField("total_count")
	@ApiModelProperty(value = "流水总记录数")
	private Long totalCount;		// 流水总记录数

	@NotNull(message="注销用户数不能为空")
    @Excel(name="注销用户数", width = 25)
    @TableField("cancel_count")
	@ApiModelProperty(value = "注销用户数")
	private Long cancelCount;		// 注销用户数

	@NotNull(message="正常用户数不能为空")
    @Excel(name="协议正常用户数", width = 25)
    @TableField("normal_ount")
	@ApiModelProperty(value = "正常用户数")
	private Long normalOunt;		// 正常用户数

}