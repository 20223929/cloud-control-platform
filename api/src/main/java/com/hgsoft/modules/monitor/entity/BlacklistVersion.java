package com.hgsoft.modules.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hgsoft.ecip.framework.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * 黑名单版本监控 Entity
 * @author 吴鉴武
 * @version 2021-04-21 02:24:32
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="黑名单版本监控", description="黑名单版本监控")
@TableName(value = "tb_blacklist_version", resultMap = "BaseResultMap")
public class BlacklistVersion extends DataEntity<BlacklistVersion> implements Serializable {

    private static final long serialVersionUID = 1L;
	/**
	 * 全量ETC黑名单版本
	 */
	private Long fullEtcBlackVersion;
	/**
	 * 全量ETC黑名单最新更新时间
	 */
	private String fullEtcBlackUpdateTime;
	/**
	 * 增量ETC黑名单版本
	 */
	private Long incrementEtcBlackVersion;
	/**
	 * 增量ETC黑名单最新更新时间
	 */
	private String incrementEtcBlackUpdateTime;
	/**
	 * 全量OBU黑名单版本
	 */
	private Long fullObuBlackVersion;
	/**
	 * 全量OBU黑名单最新更新时间
	 */
	private String fullObuBlackUpdateTime;
	/**
	 * 增量OBU黑名单版本
	 */
	private Long incrementObuBlackVersion;
	/**
	 * 增量OBU黑名单最新更新时间
	 */
	private String incrementObuBlackUpdateTime;
}