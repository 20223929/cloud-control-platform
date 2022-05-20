package com.hgsoft.modules.gdetc.service;

import com.hgsoft.modules.gdetc.entity.GdetcProfit;
import com.hgsoft.modules.gdetc.entity.QueryCondition;
import com.hgsoft.modules.report.entity.PageVo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 联合电服收益service
 * Created by 吴鉴武
 * date:2022/5/6 14:57
 */
public interface GdetcProfitService {

    /**
     * 分页查询收益数据
     *
     * @param pageVo
     * @param condition
     * @return
     */
    PageVo<GdetcProfit> findPage(PageVo<GdetcProfit> pageVo, QueryCondition condition);

    /**
     * 导出数据
     *
     * @param condition
     * @return
     */
    ModelAndView exportData(QueryCondition condition, HttpServletRequest request, HttpServletResponse response);
}
