package com.hgsoft.modules.merchant.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Date;

/**
 * 三级商户信息表 Entity
 *
 * @author 吴鉴武
 * @version 2021-04-27 02:21:57
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "三级商户信息表", description = "三级商户信息表")
@TableName(value = "tb_site", resultMap = "BaseResultMap")
public class Site extends DataEntity<Site> implements Serializable {

    private static final long serialVersionUID = 1L;

    public Site() {
        super();
        this.setIdType(IDTYPE_IDWORKER);
    }

    @Length(min = 1, max = 60, message = "平台二级商户id长度必须介于 1 和 60 之间")
    @Excel(name = "平台二级商户id", width = 25)
    @TableField("platform_merchant_id")
    @ApiModelProperty(value = "平台二级商户id")
    private String platformMerchantId;        // 平台二级商户id

    @Length(min = 1, max = 60, message = "平台三级商户id长度必须介于 1 和 60 之间")
    @Excel(name = "平台三级商户id", width = 25)
    @TableId("platform_site_id")
    @ApiModelProperty(value = "平台三级商户id")
    private String platformSiteId;        // 平台三级商户id

    @Length(min = 0, max = 60, message = "联网中心三级商户id长度必须介于 0 和 60 之间")
    @Excel(name = "联网中心三级商户id", width = 25)
    @TableField("center_site_id")
    @ApiModelProperty(value = "联网中心三级商户id")
    private String centerSiteId;        // 联网中心三级商户id

    @Length(min = 0, max = 60, message = "银行三级商户id长度必须介于 0 和 60 之间")
    @Excel(name = "银行三级商户id", width = 25)
    @TableField("bank_site_id")
    @ApiModelProperty(value = "银行三级商户id")
    private String bankSiteId;        // 银行三级商户id

    @Length(min = 1, max = 150, message = "消费场景名称长度必须介于 1 和 150 之间")
    @Excel(name = "消费场景名称", width = 25)
    @TableField("name")
    @ApiModelProperty(value = "消费场景名称")
    private String name;        // 消费场景名称

    @Length(min = 1, max = 300, message = "地址长度必须介于 1 和 300 之间")
    @Excel(name = "地址", width = 25)
    @TableField("address")
    @ApiModelProperty(value = "地址")
    private String address;        // 地址

    @Length(min = 1, max = 20, message = "纬度长度必须介于 1 和 20 之间")
    @Excel(name = "纬度", width = 25)
    @TableField("lat")
    @ApiModelProperty(value = "纬度")
    private String lat;        // 纬度

    @Length(min = 1, max = 20, message = "经度长度必须介于 1 和 20 之间")
    @Excel(name = "经度", width = 25)
    @TableField("lng")
    @ApiModelProperty(value = "经度")
    private String lng;        // 经度

    @Length(min = 1, max = 10, message = "清分机构编码长度必须介于 1 和 10 之间")
    @Excel(name = "清分机构编码", width = 25)
    @TableField("province_num")
    @ApiModelProperty(value = "清分机构编码")
    private String provinceNum;        // 清分机构编码

    @Length(min = 1, max = 10, message = "起始日期长度必须介于 1 和 10 之间")
    @Excel(name = "起始日期", width = 25)
    @TableField("start_time")
    @ApiModelProperty(value = "起始日期")
    private String startTime;        // 起始日期

    @Length(min = 1, max = 10, message = "终止日期长度必须介于 1 和 10 之间")
    @Excel(name = "终止日期", width = 25)
    @TableField("end_time")
    @ApiModelProperty(value = "终止日期")
    private String endTime;        // 终止日期

    @Length(min = 1, max = 60, message = "创建人长度必须介于 1 和 60 之间")
    @Excel(name = "创建人", width = 25)
    @TableField("creator")
    @ApiModelProperty(value = "创建人")
    private String creator;        // 创建人

    @NotNull(message = "创建时间不能为空")
    @Excel(name = "创建时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("createtime")
    @ApiModelProperty(value = "创建时间")
    private Date createtime;        // 创建时间

    @Length(min = 1, max = 60, message = "修改人长度必须介于 1 和 60 之间")
    @Excel(name = "修改人", width = 25)
    @TableField("modifier")
    @ApiModelProperty(value = "修改人")
    private String modifier;        // 修改人

    @NotNull(message = "修改时间不能为空")
    @Excel(name = "修改时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("modifytime")
    @ApiModelProperty(value = "修改时间")
    private Date modifytime;        // 修改时间

    /**
     * ================ 西藏切换需求新增字段 =====================
     */

    @TableField(value = "agent_id",strategy = FieldStrategy.IGNORED)
    private String agentId;//代理商主键编号
    @TableField(value = "agent_rate",strategy = FieldStrategy.IGNORED)
    private Double agentRate;//代理商佣金费率
    @TableField("bank")
    private String bank;//开户行
    @TableField("bank_addr")
    private String bankAddr;//开户行地址
    @TableField("bank_account")
    private String bankAccount;//开户行账号
    @TableField("center_rate")
    private Double centerRate;//中心费率
    @TableField("service_rate")
    private Double serviceRate;//服务费率
    @TableField("has_agent")
    private Integer hasAgent;//是否有代理商:0-无，1-有
    @TableField("toll_settlement_interval")
    private Integer tollSettlementInterval;//通行费结算周期，1-T+1；2-T+4；3-月结
    @TableField("service_settlement_interval")
    private Integer serviceSettlementInterval;//服务费结算周期，1-T+1；2-T+4；3-月结
}