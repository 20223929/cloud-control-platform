package com.hgsoft.modules.agent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

import java.io.Serializable;
import java.util.Date;

/**
 * 代理商entity
 *
 * @author wjw
 * @version 2022-04-29 09:47:31
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "代理商表", description = "代理商表")
@TableName(value = "tb_agent", resultMap = "BaseResultMap")
public class Agent extends DataEntity<Agent> implements Serializable {

    private static final long serialVersionUID = 1L;

    public Agent() {
        super();
        this.setIdType(IDTYPE_IDWORKER);
    }

    @TableId(value = "sys_id",type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    @ApiModelProperty(value = "系统编号")
    private Long sysId;        // 系统编号

    @Excel(name = "代理商编号", width = 25)
    @TableField("agent_no")
    @ApiModelProperty(value = "代理商编号")
    private String agentNo;        // 代理商编号

    @Excel(name = "代理商名称", width = 25)
    @TableField("agent_name")
    @ApiModelProperty(value = "代理商名称")
    private String agentName;        // 代理商名称

    @TableField("certificate_type")
    @ApiModelProperty(value = "证件类型")
    private Integer certificateType;        // 证件类型

    @Excel(name = "证件类型", width = 25)
    private String certificateTypeDesc;

    @Excel(name = "纳税人识别号", width = 25)
    @TableField("taxpayer_code")
    @ApiModelProperty(value = "纳税人识别号")
    private String taxpayerCode;        // 纳税人识别号

    @Excel(name = "统一社会信用代码/组织机构代码证号", width = 50)
    @TableField("credit_code")
    @ApiModelProperty(value = "统一社会信用代码/组织机构代码证号")
    private String creditCode;        // 统一社会信用代码/组织机构代码证号

    @Excel(name = "开户行", width = 25)
    @TableField("bank")
    @ApiModelProperty(value = "开户行")
    private String bank;        // 开户行

    @Excel(name = "开户行地址", width = 25)
    @TableField("bank_addr")
    @ApiModelProperty(value = "开户行地址")
    private String bankAddr;        // 开户行地址

    @Excel(name = "开户行账号", width = 25)
    @TableField("bank_account")
    @ApiModelProperty(value = "开户行账号")
    private String bankAccount;        // 开户行账号

    @Excel(name = "创建时间", width = 25,format = "yyyy-MM-dd HH:mm:ss")
    @TableField("createtime")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;        // 创建时间

    @Excel(name = "更新时间", width = 25,format = "yyyy-MM-dd HH:mm:ss")
    @TableField("modifytime")
    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;        // 更新时间

//    @Excel(name = "备注", width = 25)
    @TableField("remark")
    @ApiModelProperty(value = "备注")
    private String remark;        // 备注
}