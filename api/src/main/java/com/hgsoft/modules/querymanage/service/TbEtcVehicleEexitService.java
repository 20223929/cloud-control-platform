package com.hgsoft.modules.querymanage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.modules.querymanage.entity.TbEtcVehicleEexit;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public interface TbEtcVehicleEexitService {
    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, TbEtcVehicleEexit s);

    /**
     * 下载数据导入模板
     */
    ModelAndView exportTemplate(HttpServletRequest request);

    /**
     * 真正也上使用这个查询结果
     * @param page
     * @param entity
     * @return
     */
    IPage<TbEtcVehicleEexit> findSearchDataPage(Page<TbEtcVehicleEexit> page, TbEtcVehicleEexit entity);
}
