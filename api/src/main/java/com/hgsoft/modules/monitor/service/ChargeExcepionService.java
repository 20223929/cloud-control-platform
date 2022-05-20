package com.hgsoft.modules.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.monitor.entity.ChargeExcepion;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 收费异常管理Service
 * @author 吴鉴武
 * @version 2021-04-21 22:35:10
 */
public interface ChargeExcepionService extends CrudService<ChargeExcepion> {

    IPage<ChargeExcepion> chargeExcepionPage(Page<ChargeExcepion> page, ChargeExcepion chargeExcepion);

    /**
     * 确认收费异常记录
     * @param list
     */
    void confirm(List<ChargeExcepion> list);

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, ChargeExcepion chargeExcepion);
}