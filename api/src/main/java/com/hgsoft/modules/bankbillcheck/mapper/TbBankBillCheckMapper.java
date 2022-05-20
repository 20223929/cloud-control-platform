package com.hgsoft.modules.bankbillcheck.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheck;
import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheckSearchVo;
import com.hgsoft.modules.bankbillcheck.entity.TbOnlineEexit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 银行对账核对表MAPPER接口
 * 
 * @author 何志豪
 * @version 2021-04-18 23:35:35
 */
@Repository("com.hgsoft.modules.bankbillcheck.mapper.TbBankBillCheckMapper")
public interface TbBankBillCheckMapper extends DataMapper<TbBankBillCheck> {
	public IPage<TbBankBillCheck> findSearchDataPage(IPage<TbBankBillCheck> page,
			@Param("entity") TbBankBillCheckSearchVo entity);

	/**
	 *
	 * @param entity
	 * @return
	 */
	public List<TbBankBillCheck> findAllList(TbBankBillCheckSearchVo entity);

	/**
	 * 插入汇总数据
	 * @param tbBankBillCheck
	 */
	public void insertSumRecord(@Param("entity")TbBankBillCheck tbBankBillCheck);
	
	/**
	 * 插入明细数据
	 * @param tbBankBillCheck
	 */
	public void insertDetailRecord(@Param("entity")TbBankBillCheck tbBankBillCheck);

	/**
	 * 获取某个查询的合计
	 * @param tbBankBillCheck
	 */
	public TbBankBillCheck getSum(TbBankBillCheckSearchVo tbBankBillCheck);

    List<TbOnlineEexit> findAllEexit(TbBankBillCheckSearchVo checks);

	Long findAllEexitCount(TbBankBillCheckSearchVo checks);
}