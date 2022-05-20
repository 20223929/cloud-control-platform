package com.hgsoft.modules.merchant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.Instant;

/**
 * 平台接入商户证书
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "商户证书表", description = "商户证书表")
@TableName(value = "platform_merchant_cert", resultMap = "BaseResultMap")
public class MerchantCert implements Serializable {

    private static final long serialVersionUID = 5606988187490447363L;
    @TableId(value = "sys_id", type = IdType.AUTO)
    private Long sysId;

    /**
     * 商户编号
     */
    @TableField("mch_id")
    private String mchId;

    /**
     * 证书公钥
     */
    @TableField("public_key")
    private String publicKey;

    /**
     * 证书私钥
     */
    @TableField("private_key")
    private String privateKey;

    /**
     * 证书序列号
     */
    @TableField("serial_no")
    private String serialNo;

    /**
     * 证书生效日期
     */
    @TableField("start_time")
    private String startTime;

    /**
     * 证书月效期
     */
    @TableField("end_time")
    private String endTime;

    /**
     * 证书名称
     */
    @TableField("name")
    private String name;

    /**
     * 证书描述
     */
    @TableField("description")
    private String description;
    @TableField("created_by")
    private String createdBy;
    @TableField("updated_by")
    private String updatedBy;
    @TableField("created_time")
    private Instant createdTime;
    @TableField("updated_time")
    private Instant updatedTime;
    @TableField("exist = false")
    private Integer delFlag;

}
