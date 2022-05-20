package com.hgsoft.modules.bankbillcheck.mapper;

import com.hgsoft.modules.bankbillcheck.entity.*;
import com.hgsoft.modules.report.entity.PageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 吴鉴武 on 2021/7/16 10:31
 */
@Repository("com.hgsoft.modules.bankbillcheck.mapper.BankCheckMapper")
public interface BankCheckMapper {

    /**
     * 分页查询新银行对账信息
     * @param page
     * @param bankCheck
     * @return
     */
    PageVo<BankCheck> findBankCheckPage(PageVo<BankCheck> page,@Param("entity") BankCheck bankCheck);

    /**
     * 分页查询异常明细
     * @param page
     * @param bankCheckException
     * @return
     */
    PageVo<BankCheckException> findBankCheckExceptionPage(PageVo<BankCheckException> page,@Param("entity") BankCheckException bankCheckException);

    /**
     * 查询数据列表
     * @param bankCheck
     * @return
     */
    List<BankCheck> findList(@Param("entity") BankCheck bankCheck);

    /**
     *  查询异常信息列表
     * @param bankCheckException
     * @return
     */
    List<BankCheckException> findExceptionList(@Param("entity") BankCheckException bankCheckException);

    /**
     * 查询详情列表
     * @param bankCheckDetail
     * @return
     */
    List<BankCheckDetail> findDetailList(@Param("entity") BankCheckDetail bankCheckDetail);

    /**
     * 查找确认列表
     * @param list
     * @return
     */
    List<BankCheckConfirm> findConfirmList(@Param("list") List<BankCheckConfirm> list);

    /**
     * 更新确认状态
     * @param bankCheckConfirm
     */
    void updateConfirmData(@Param("entity") BankCheckConfirm bankCheckConfirm);

    /**
     * 批量插入确认信息
     * @param list
     */
    void insertConfirmData(@Param("list")List<BankCheckConfirm> list);

    /**
     * 查询导出流水明细
     * @param bankCheck
     * @return
     */
    List<BankCheckDetailExport> exportDetailList(@Param("entity") BankCheck bankCheck);

    /**
     * 合计
     * @param bankCheck
     * @return
     */
    BankCheck getSum(@Param("entity") BankCheck bankCheck);
}
