package com.hgsoft.modules.bankbillcheck.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.CrudService;
import com.hgsoft.modules.bankbillcheck.entity.TbCenterBillCheck;
import com.hgsoft.modules.bankbillcheck.entity.TbCenterBillCheckSearchVo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 联网中心对账核对表Service
 * @author 何志豪
 * @version 2021-04-21 02:30:17
 */
public interface TbCenterBillCheckService extends CrudService<TbCenterBillCheck> {

    TbCenterBillCheck getByPrimaryKey(String transDate, String merchantGroupId, String merchantId, String siteId);

    /**
     * 导出数据
     */
    ModelAndView exportData(HttpServletRequest request, TbCenterBillCheckSearchVo tbCenterBillCheck);

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
    IPage<TbCenterBillCheck> findSearchDataPage(Page<TbCenterBillCheck> page, TbCenterBillCheckSearchVo entity);

    /**
     * 真正也上使用这个查询结果
     * @param entity
     * @return
     */
    List<TbCenterBillCheck> findSearchDataAll(TbCenterBillCheckSearchVo entity);

    /**
     * 首次确认
     * @param entity
     */
    void confirm(TbCenterBillCheckSearchVo entity);

    /**
     * 补确认
     * @param entity
     */
    void secondConfirm(TbCenterBillCheckSearchVo entity);

    /**
     * 统计流水明细
     * @param vo
     * @return
     */
    Long countEexit(TbCenterBillCheckSearchVo vo);

    /**
     * 导出流水明细
     * @param request
     * @param vo
     * @return
     */
    ModelAndView exportEexitDetailData(HttpServletRequest request, TbCenterBillCheckSearchVo vo);
}