package com.hgsoft.modules.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * 省中心清分对账报表
 * @author 吴鉴武
 * @date 2021-04-19 23:29:04
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ClearAccountReport extends DataEntity<ClearAccountReport> implements Serializable {

    private static final long serialVersionUID = 1L;

    public ClearAccountReport() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	/**
	 * 交易日期范围(查询使用)
	 */
	private String[] timeScope;
	private Integer nodeLevel;
	private Integer value;

	/**
     * 一级运营商
     */
	private String merchantGroupId;
	@Excel(name = "拓展方",width = 25)
	private String merchantGroupName;
	/**
     * 二级运营商
     */
	private String merchantId;
	@Excel(name = "运营方",width = 25)
	private String merchantName;
	/**
     * 三级运营商
     */
	private String siteId;
	@Excel(name = "场景",width = 25)
	private String siteName;
	/**
     * 交易日期
     */
	@Excel(name = "交易日期",width = 25)
	private String tradeDay;
	/**
	 * 交易流水数
	 */
	private Long totalCount;
	/**
	 * 交易流水实收金额(元)
	 */
	@Excel(name = "交易流水实收金额(元)",width = 25)
	private String totalActualFee;
	/**
	 * 已结算流水数
	 */
	@Excel(name = "已结算流水数",width = 25)
	private Long settlementCount;
	/**
	 *已结算商户应收总金额(元)
	 */
	@Excel(name = "已结算商户应收总金额(元)",width = 25)
	private String settlementMerchantTotalFee;
	/**
	 * 已结算与已清分流水数差值
	 */
	@Excel(name = "已结算与已清分流水数差值",width = 25)
	private Long differCount;
	/**
	 * 已结算与已清分金额差额(元)
	 */
	@Excel(name = "已结算与已清分金额差额(元)",width = 25)
	private String differFee;
	/**
	 * 已清分流水数
	 */
	@Excel(name = "已清分流水数",width = 25)
	private Long clearCount;
	/**
	 *已清分总金额(元)
	 */
	@Excel(name = "已清分总金额(元)",width = 25)
	private String clearFee;
	/**
	 * 待清分流水数
	 */
	@Excel(name = "待清分流水数",width = 25)
	private Long unclearCount;
	/**
	 *待清分总金额(元)
	 */
	@Excel(name = "待清分总金额(元)",width = 25)
	private String unclearFee;
	/**
	 * 异常流水数
	 */
	@Excel(name = "异常流水数",width = 25)
	private Long exceptionCount;
	/**
	 *异常流水总金额(元)
	 */
	@Excel(name = "异常流水总金额(元)",width = 25)
	private String exceptionFee;

	/**
	 * 根据商户信息控制用户权限
	 */
	@TableField(exist = false)
	private UserMerchantPermissionswrapper.UserMerchantParam userMerchantParam;
}