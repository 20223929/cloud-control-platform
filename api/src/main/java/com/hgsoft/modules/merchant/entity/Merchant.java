package com.hgsoft.modules.merchant.entity;

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
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 二级商户信息表 Entity
 *
 * @author 吴鉴武
 * @version 2021-04-27 02:20:09
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "二级商户信息表", description = "二级商户信息表")
@TableName(value = "tb_merchant", resultMap = "BaseResultMap")
public class Merchant extends DataEntity<Merchant> implements Serializable {

    private static final long serialVersionUID = 1L;

    public Merchant() {
        super();
        this.setIdType(IDTYPE_IDWORKER);
    }

    @Length(min = 1, max = 60, message = "平台一级商户id长度必须介于 1 和 60 之间")
    @Excel(name = "平台一级商户id", width = 25)
    @TableField("platform_merchant_group_id")
    @ApiModelProperty(value = "平台一级商户id")
    private String platformMerchantGroupId;        // 平台一级商户id

    @Length(min = 1, max = 60, message = "平台二级商户id长度必须介于 1 和 60 之间")
    @Excel(name = "平台二级商户id", width = 25)
    @TableId("platform_merchant_id")
    @ApiModelProperty(value = "平台二级商户id")
    private String platformMerchantId;        // 平台二级商户id

    @Length(min = 0, max = 60, message = "联网中心二级商户id长度必须介于 0 和 60 之间")
    @Excel(name = "联网中心二级商户id", width = 25)
    @TableField("center_merchant_id")
    @ApiModelProperty(value = "联网中心二级商户id")
    private String centerMerchantId;        // 联网中心二级商户id

    @Length(min = 0, max = 60, message = "银行二级商户id长度必须介于 0 和 60 之间")
    @Excel(name = "银行二级商户id", width = 25)
    @TableField("bank_merchant_id")
    @ApiModelProperty(value = "银行二级商户id")
    private String bankMerchantId;        // 银行二级商户id

    @NotNull(message = "服务类型不能为空")
    @Excel(name = "服务类型", width = 25)
    @TableField("service_type")
    @ApiModelProperty(value = "服务类型")
    private Integer serviceType;        // 服务类型

    @Length(min = 1, max = 150, message = "运营方名称长度必须介于 1 和 150 之间")
    @Excel(name = "运营方名称", width = 25)
    @TableField("name")
    @ApiModelProperty(value = "运营方名称")
    private String name;        // 运营方名称

    @NotNull(message = "证件类型不能为空")
    @Excel(name = "证件类型", width = 25)
    @TableField("certificate_type")
    @ApiModelProperty(value = "证件类型")
    private Integer certificateType;        // 证件类型

    @Length(min = 1, max = 20, message = "纳税人识别号长度必须介于 1 和 20 之间")
    @Excel(name = "纳税人识别号", width = 25)
    @TableField("taxpayer_code")
    @ApiModelProperty(value = "纳税人识别号")
    private String taxpayerCode;        // 纳税人识别号

    @Length(min = 1, max = 20, message = "统一社会信用代码/组织机构代码证号长度必须介于 1 和 20 之间")
    @Excel(name = "统一社会信用代码/组织机构代码证号", width = 25)
    @TableField("credit_code")
    @ApiModelProperty(value = "统一社会信用代码/组织机构代码证号")
    private String creditCode;        // 统一社会信用代码/组织机构代码证号

    @Length(min = 1, max = 20, message = "联系电话长度必须介于 1 和 20 之间")
    @Excel(name = "联系电话", width = 25)
    @TableField("tel")
    @ApiModelProperty(value = "联系电话")
    private String tel;        // 联系电话

    @Length(min = 1, max = 150, message = "联系人长度必须介于 1 和 150 之间")
    @Excel(name = "联系人", width = 25)
    @TableField("contact")
    @ApiModelProperty(value = "联系人")
    private String contact;        // 联系人

    @Length(min = 1, max = 300, message = "地址长度必须介于 1 和 300 之间")
    @Excel(name = "地址", width = 25)
    @TableField("address")
    @ApiModelProperty(value = "地址")
    private String address;        // 地址

    @Length(min = 1, max = 300, message = "开户行长度必须介于 1 和 300 之间")
    @Excel(name = "开户行", width = 25)
    @TableField("bank")
    @ApiModelProperty(value = "开户行")
    private String bank;        // 开户行

    @Length(min = 1, max = 300, message = "开户行地址长度必须介于 1 和 300 之间")
    @Excel(name = "开户行地址", width = 25)
    @TableField("bank_addr")
    @ApiModelProperty(value = "开户行地址")
    private String bankAddr;        // 开户行地址

    @Length(min = 1, max = 20, message = "开户行账号长度必须介于 1 和 20 之间")
    @Excel(name = "开户行账号", width = 25)
    @TableField("bank_account")
    @ApiModelProperty(value = "开户行账号")
    private String bankAccount;        // 开户行账号

    @NotNull(message = "银行费率不能为空")
    @Excel(name = "银行费率", width = 25)
    @TableField("bank_rate")
    @ApiModelProperty(value = "银行费率")
    private Double bankRate;        // 银行费率

    @NotNull(message = "清分服务费率不能为空")
    @Excel(name = "清分服务费率", width = 25)
    @TableField("center_rate")
    @ApiModelProperty(value = "清分服务费率")
    private Double centerRate;        // 清分服务费率

    @NotNull(message = "清分服务费结算方式不能为空")
    @Excel(name = "清分服务费结算方式", width = 25)
    @TableField("center_settlement_type")
    @ApiModelProperty(value = "清分服务费结算方式")
    private Integer centerSettlementType;        // 清分服务费结算方式

    @NotNull(message = "结算周期不能为空")
    @Excel(name = "结算周期", width = 25)
    @TableField("center_settlement_cycle")
    @ApiModelProperty(value = "结算周期")
    private Integer centerSettlementCycle;        // 结算周期

    @Length(min = 1, max = 60, message = "创建人长度必须介于 1 和 60 之间")
    @Excel(name = "创建人", width = 25)
    @TableField("creator")
    @ApiModelProperty(value = "创建人")
    private String creator;        // 创建人

    @NotNull(message = "创建时间不能为空")
    @Excel(name = "创建时间", width = 25, format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("modifytime")
    @ApiModelProperty(value = "修改时间")
    private Date modifytime;        // 修改时间

//    //证书文件信息
//    @TableField(exist = false)
//    private MultipartFile certFile;

    //是否是新证书文件
    @TableField(exist = false)
    private Boolean isNewFile;

    @TableField(exist = false)
    private String certFileName;

}