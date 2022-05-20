package com.hgsoft.modules.report.service;

import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.ServiceRecvReport;

import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.ecip.framework.common.response.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 服务方应收报表Service
 * @author 雷新辉
 * @version 2022-04-28 05:44:26
 */
public interface ServiceRecvReportService extends CrudService<ServiceRecvReport> {

    PageVo<ServiceRecvReport> serviceRecvReportPage(PageVo<ServiceRecvReport> page, ServiceRecvReport serviceRecvReport);

    ServiceRecvReport getByPrimaryKey(String sysId);


    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, ServiceRecvReport serviceRecvReport);

}