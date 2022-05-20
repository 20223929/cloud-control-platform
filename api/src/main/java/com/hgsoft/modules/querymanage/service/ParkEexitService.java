package com.hgsoft.modules.querymanage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.querymanage.entity.ParkEexit;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
/**
 * 停车场流水查询Service
 * @author Andy
 * @version 2021-04-16 22:34:19
 */
public interface ParkEexitService extends CrudService<ParkEexit> {

    /**
     * 分页查询停车场流水数据
     * @param page
     * @param parkEexit
     * @return
     */
    IPage<ParkEexit> parkEexitPage(Page<ParkEexit> page, ParkEexit parkEexit);

    /**
     * 查询单挑停车场流水
     * @param parkEexit
     * @return
     */
    ParkEexit findUniqueByProperty(ParkEexit parkEexit);

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, ParkEexit parkEexit);
}