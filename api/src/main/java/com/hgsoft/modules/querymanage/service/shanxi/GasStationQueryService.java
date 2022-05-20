package com.hgsoft.modules.querymanage.service.shanxi;

import com.hgsoft.modules.querymanage.entity.shanxi.GasStationQuery;
import com.hgsoft.modules.report.entity.PageVo;
import org.springframework.web.servlet.ModelAndView;

/**
 * 加油站流水查询service
 * Created by 吴鉴武 on 2021/5/7 8:53
 */
public interface GasStationQueryService {

    /**
     * 分页查询加油站流水
     *
     * @param page
     * @param gasStationQuery
     * @return
     */
    PageVo<GasStationQuery> findPage(PageVo<GasStationQuery> page, GasStationQuery gasStationQuery);

    /**
     * 导出excel
     *
     * @param gasStationQuery
     * @return
     */
    ModelAndView exportExcel(GasStationQuery gasStationQuery);

    /**
     * 通过交易流水号查询加油站流水
     *
     * @param queryParam
     * @return
     */
    GasStationQuery findDataById(GasStationQuery queryParam);
}
