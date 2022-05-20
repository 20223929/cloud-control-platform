package com.hgsoft.modules.querymanage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.querymanage.entity.TbOnlineRefundEexit;
import com.hgsoft.modules.report.entity.PageVo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
/**
 * 银行退费流水查询Service
 * @author 雷新辉
 * @version 2021-04-22 09:25:22
 */
public interface TbOnlineRefundEexitService extends CrudService<TbOnlineRefundEexit> {

    IPage<TbOnlineRefundEexit> tbOnlineRefundEexitPage(PageVo<TbOnlineRefundEexit> page, TbOnlineRefundEexit tbOnlineRefundEexit);

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, TbOnlineRefundEexit tbOnlineRefundEexit);

    /**
     * 下载数据导入模板
     */
    ModelAndView exportTemplate(HttpServletRequest request);

    TbOnlineRefundEexit findUniqueByProperty(String tbRefundApplySysId);
}