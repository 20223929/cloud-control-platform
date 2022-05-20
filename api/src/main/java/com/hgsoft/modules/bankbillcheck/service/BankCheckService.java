package com.hgsoft.modules.bankbillcheck.service;

import com.hgsoft.modules.bankbillcheck.entity.*;
import com.hgsoft.modules.report.entity.PageVo;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 新银行对账服务类
 * Created by 吴鉴武 on 2021/7/16 16:51
 */
public interface BankCheckService {

    /**
     * 分页查询
     * @param pageVo
     * @param bankCheck
     * @return
     */
    PageVo<BankCheck> findBankCheckPage(PageVo<BankCheck> pageVo,BankCheck bankCheck);

    /**
     * 分页查询
     * @param pageVo
     * @param bankCheckException
     * @return
     */
    PageVo<BankCheckException> findBankCheckExceptionPage(PageVo<BankCheckException> pageVo,BankCheckException bankCheckException);

    /**
     * 查询明细列表
     * @param bankCheckDetail
     * @return
     */
    List<BankCheckDetail> findDetailList(BankCheckDetail bankCheckDetail);

    /**
     * 保存确认信息
     * @param list
     * @return
     */
    ResponseEntity saveConfirmData(List<BankCheckConfirm> list);

    /**
     * 导出Excel
     * @param bankCheck
     * @return
     */
    ModelAndView exportExcel(BankCheck bankCheck);

    /**
     * 导出异常明细
     * @param bankCheckException
     * @return
     */
    ModelAndView exportExceptionExcel(BankCheckException bankCheckException);

    /**
     * 导出流水明细
     * @param bankCheck
     * @return
     */
    ModelAndView exportDetailExcel(BankCheck bankCheck);
}
