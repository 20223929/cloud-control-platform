package com.hgsoft.modules.bankbillcheck.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.bankbillcheck.entity.TbCenterBillCheck;
import com.hgsoft.modules.bankbillcheck.entity.TbCenterBillCheckSearchVo;
import com.hgsoft.modules.bankbillcheck.entity.TbEtcTransactionEexit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 联网中心对账核对表MAPPER接口
 *
 * @author 何志豪
 * @version 2021-04-21 02:30:17
 */
@Repository("com.hgsoft.modules.bankbillcheck.mapper.TbCenterBillCheckMapper")
public interface TbCenterBillCheckMapper extends DataMapper<TbCenterBillCheck> {

    /**
     * 用于分页查询
     * @param page
     * @param entity
     * @return
     */
    public IPage<TbCenterBillCheck> findSearchDataPage(IPage<TbCenterBillCheck> page,
                                                       @Param("entity") TbCenterBillCheckSearchVo entity);

    /**
     * 用于导出
     * @param entity
     * @return
     */
    public List<TbCenterBillCheck> findSearchDataAll(
            @Param("entity") TbCenterBillCheckSearchVo entity);

    /**
     * 插入汇总数据
     *
     * @param param
     */
    public void insertSumRecord(@Param("entity") TbCenterBillCheckSearchVo param);

    /**
     * 插入明细数据
     *
     * @param param
     */
    public void insertDetailRecord(@Param("entity") TbCenterBillCheckSearchVo param);

    /**
     * 获取某个查询的合计
     *
     * @param param
     */
    public TbCenterBillCheck getSum(@Param("entity") TbCenterBillCheckSearchVo param);

    /**
     * 更新汇总数据
     *
     * @param param
     */
    public void updateSumForSecondConfirm(@Param("entity") TbCenterBillCheckSearchVo param);

    /**
     * 更新明细数据
     *
     * @param param
     */
    public void updateDetailForSecondConfirm(@Param("entity") TbCenterBillCheckSearchVo param);

    /**
     * 查询流水数量
     * @param vo
     * @return
     */
    Long findAllEexitCount(@Param("entity") TbCenterBillCheckSearchVo vo);

    List<TbEtcTransactionEexit> findAllEexit(@Param("entity") TbCenterBillCheckSearchVo vo);
}