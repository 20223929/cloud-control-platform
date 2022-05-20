package com.hgsoft.modules.report.mapper;

import com.hgsoft.modules.report.entity.SettlementReport;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 资金结算报表MAPPER接口
 * @author 郑裕强
 * @version 2022-05-04 23:25:53
 */
@Repository("com.hgsoft.modules.report.mapper.SettlementReportMapper")
public interface SettlementReportMapper extends DataMapper<SettlementReport> {

    /**
     * 根据条件查询汇总信息
     * @param settlementReport
     * @return
     */
    SettlementReport getSum(@Param("entity") SettlementReport settlementReport);

    int updateBankTransferSta(@Param("sysId") String sysId, @Param("bankTransferSta") Integer bankTransferSta);
}