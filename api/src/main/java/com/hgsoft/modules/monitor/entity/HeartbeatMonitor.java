package com.hgsoft.modules.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
/**
 * 车道监控 Entity
 * @author 吴鉴武
 * @version 2021-06-01 22:47:13
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="车道监控", description="车道监控")
@TableName(value = "tb_heartbeat_eexit", resultMap = "BaseResultMap")
public class HeartbeatMonitor extends DataEntity<HeartbeatMonitor> implements Serializable {

    private static final long serialVersionUID = 1L;

    public HeartbeatMonitor() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

    private String searchId;
    private Integer nodeLevel;
    private UserMerchantPermissionswrapper.UserMerchantParam userMerchantParam;

	@NotNull(message="系统编号不能为空")
    @TableId("sys_id")
	@ApiModelProperty(value = "系统编号")
	private Long sysId;		// 系统编号

	@Length(min=1, max=50, message="心跳流水号长度必须介于 1 和 50 之间")
    @TableField("heartbeat_trans_id")
	@ApiModelProperty(value = "心跳流水号")
	private String heartbeatTransId;		// 心跳流水号

	@Excel(name = "拓展方",width = 25)
	private String merchantGroupName;
	@Excel(name = "运营方",width = 25)
	private String merchantName;
	@Excel(name = "场景",width = 25)
	private String siteName;

	@Length(min=1, max=60, message="三级运营商长度必须介于 1 和 60 之间")
    @TableField("site_id")
	@ApiModelProperty(value = "三级运营商")
	private String siteId;		// 三级运营商

	@Length(min=1, max=60, message="车道号长度必须介于 1 和 60 之间")
    @Excel(name="车道号", width = 25)
    @TableField("equipment_id")
	@ApiModelProperty(value = "车道号")
	private String equipmentId;		// 车道号

	@NotNull(message="服务类型不能为空")
    @TableField("service_type")
	@ApiModelProperty(value = "服务类型")
	private Integer serviceType;		// 服务类型

	@Excel(name="服务类型", width = 25)
	private String serviceTypeName;

	@Length(min=0, max=30, message="当前ETC卡黑名单版本号，全量与增量，以“|”分隔长度必须介于 0 和 30 之间")
    @TableField("card_blacklist_version")
	@ApiModelProperty(value = "当前ETC卡黑名单版本号，全量与增量，以“|”分隔")
	private String cardBlacklistVersion;		// 当前ETC卡黑名单版本号，全量与增量，以“|”分隔

	@Length(min=0, max=30, message="当前OBU黑名单版本号，全量与增量，以“|”分隔长度必须介于 0 和 30 之间")
    @TableField("obu_blackelist_version")
	@ApiModelProperty(value = "当前OBU黑名单版本号，全量与增量，以“|”分隔")
	private String obuBlackelistVersion;		// 当前OBU黑名单版本号，全量与增量，以“|”分隔

	@Length(min=1, max=300, message="场所软件版本号，涉及多个场所软件版本号以“|”分隔长度必须介于 1 和 300 之间")
    @Excel(name="车道软件版本", width = 25)
    @TableField("site_soft_version")
	@ApiModelProperty(value = "场所软件版本号，涉及多个场所软件版本号以“|”分隔")
	private String siteSoftVersion;		// 场所软件版本号，涉及多个场所软件版本号以“|”分隔

	@Length(min=1, max=30, message="场所软件状态，0-无响应、1-正常响应、2-无设备，涉及多个场所软件状态以“|”分隔长度必须介于 1 和 30 之间")
    @Excel(name="车道软件状态", width = 25)
    @TableField("site_soft_state")
	@ApiModelProperty(value = "场所软件状态，0-无响应、1-正常响应、2-无设备，涉及多个场所软件状态以“|”分隔")
	private String siteSoftState;		// 场所软件状态，0-无响应、1-正常响应、2-无设备，涉及多个场所软件状态以“|”分隔

	@Length(min=1, max=30, message="天线状态长度必须介于 1 和 30 之间")
    @Excel(name="天线状态", width = 25)
    @TableField("antenna_state")
	@ApiModelProperty(value = "天线状态")
	private String antennaState;		// 天线状态

	@Length(min=0, max=300, message="天线品牌，涉及多个天线品牌以“|”分隔长度必须介于 0 和 300 之间")
    @Excel(name="天线品牌", width = 25)
    @TableField("antenna_brand")
	@ApiModelProperty(value = "天线品牌，涉及多个天线品牌以“|”分隔")
	private String antennaBrand;		// 天线品牌，涉及多个天线品牌以“|”分隔

	@Length(min=0, max=300, message="天线型号，涉及多个天线型号以“|”分隔长度必须介于 0 和 300 之间")
    @Excel(name="天线型号", width = 25)
    @TableField("antenna_model")
	@ApiModelProperty(value = "天线型号，涉及多个天线型号以“|”分隔")
	private String antennaModel;		// 天线型号，涉及多个天线型号以“|”分隔

	@Length(min=0, max=300, message="ETC盒子品牌，涉及多个ETC盒子品牌以“|”分隔长度必须介于 0 和 300 之间")
    @Excel(name="ETC盒子品牌", width = 25)
    @TableField("etc_box_brand")
	@ApiModelProperty(value = "ETC盒子品牌，涉及多个ETC盒子品牌以“|”分隔")
	private String etcBoxBrand;		// ETC盒子品牌，涉及多个ETC盒子品牌以“|”分隔

	@Length(min=0, max=300, message="ETC盒子型号，涉及多个ETC盒子型号以“|”分隔长度必须介于 0 和 300 之间")
    @Excel(name="ETC盒子型号", width = 25)
    @TableField("etc_box_model")
	@ApiModelProperty(value = "ETC盒子型号，涉及多个ETC盒子型号以“|”分隔")
	private String etcBoxModel;		// ETC盒子型号，涉及多个ETC盒子型号以“|”分隔

	@NotNull(message="大额交易限制配置，0-未配置、1-已开启、2-未开启不能为空")
    @TableField("max_trans_fee_config")
	@ApiModelProperty(value = "大额交易限制配置，0-未配置、1-已开启、2-未开启")
	private Integer maxTransFeeConfig;		// 大额交易限制配置，0-未配置、1-已开启、2-未开启
	@Excel(name="大额交易配置", width = 25)
	private String maxTransFeeConfigName;
	@Excel(name="全量ETC卡黑名单版本号", width = 25)
	private String cardBlackAllVersion;
	@Excel(name="增量ETC卡黑名单版本号", width = 25)
	private String cardBlackAddVersion;
	@Excel(name="全量OBU黑名单版本号", width = 25)
	private String obuBlackAllVersion;
	@Excel(name="增量OBU黑名单版本号", width = 25)
	private String obuBlackAddVersion;

	@Length(min=1, max=60, message="设备编号长度必须介于 1 和 60 之间")
    @TableField("device_id")
	@ApiModelProperty(value = "设备编号")
	private String deviceId;		// 设备编号

	@NotNull(message="创建时间不能为空")
    @Excel(name="心跳流水时间", width = 25)
    @TableField("create_time")
	@ApiModelProperty(value = "创建时间")
	private String createTime;		// 创建时间

	private Integer laneStatus; //车道状态 1-正常 2-异常

}