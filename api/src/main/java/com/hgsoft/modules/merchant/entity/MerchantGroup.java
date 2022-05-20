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

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
/**
 * 一级商户信息 Entity
 * @author 吴鉴武
 * @version 2021-04-27 02:14:30
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="一级商户信息", description="一级商户信息")
@TableName(value = "tb_merchant_group", resultMap = "BaseResultMap")
public class MerchantGroup extends DataEntity<Merchant> implements Serializable {

    private static final long serialVersionUID = 1L;

    public MerchantGroup() {
        super();
        this.setIdType(IDTYPE_IDWORKER);
    }

    @Length(min=1, max=60, message="平台一级商户id长度必须介于 1 和 60 之间")
    @Excel(name="平台一级商户id", width = 25)
    @TableId("platform_merchant_group_id")
    @ApiModelProperty(value = "平台一级商户id")
    private String platformMerchantGroupId;		// 平台一级商户id

    @Length(min=1, max=60, message="银行一级商户id长度必须介于 1 和 60 之间")
    @Excel(name="银行一级商户id", width = 25)
    @TableField("bank_merchant_group_id")
    @ApiModelProperty(value = "银行一级商户id")
    private String bankMerchantGroupId;		// 银行一级商户id

    @Length(min=1, max=150, message="名称长度必须介于 1 和 150 之间")
    @Excel(name="名称", width = 25)
    @TableField("name")
    @ApiModelProperty(value = "名称")
    private String name;		// 名称

    @Length(min=1, max=150, message="联系人长度必须介于 1 和 150 之间")
    @Excel(name="联系人", width = 25)
    @TableField("contact")
    @ApiModelProperty(value = "联系人")
    private String contact;		// 联系人

    @Length(min=1, max=20, message="联系电话长度必须介于 1 和 20 之间")
    @Excel(name="联系电话", width = 25)
    @TableField("tel")
    @ApiModelProperty(value = "联系电话")
    private String tel;		// 联系电话

    @Length(min=1, max=60, message="创建人长度必须介于 1 和 60 之间")
    @Excel(name="创建人", width = 25)
    @TableField("creator")
    @ApiModelProperty(value = "创建人")
    private String creator;		// 创建人

    @NotNull(message="创建时间不能为空")
    @Excel(name="创建时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("createtime")
    @ApiModelProperty(value = "创建时间")
    private Date createtime;		// 创建时间

    @Length(min=1, max=60, message="修改人长度必须介于 1 和 60 之间")
    @Excel(name="修改人", width = 25)
    @TableField("modifier")
    @ApiModelProperty(value = "修改人")
    private String modifier;		// 修改人

    @NotNull(message="修改时间不能为空")
    @Excel(name="修改时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("modifytime")
    @ApiModelProperty(value = "修改时间")
    private Date modifytime;		// 修改时间

}