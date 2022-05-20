package com.hgsoft.modules.report.service.shanxi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.modules.report.entity.shanxi.EtcOffLineSettlement;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public interface EtcOffLineSettlementService {

    IPage<EtcOffLineSettlement> findSearchDataPage(Page<EtcOffLineSettlement> page, EtcOffLineSettlement vo);

    ModelAndView exportTemplate(HttpServletRequest request);

    ModelAndView exportData(HttpServletRequest request, EtcOffLineSettlement vo);
}
