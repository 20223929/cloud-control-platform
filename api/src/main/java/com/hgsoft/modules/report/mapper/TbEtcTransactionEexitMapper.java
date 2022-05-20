package com.hgsoft.modules.report.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.report.entity.MerchantAccountReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ETC交易流水表MAPPER接口
 * @author 吴鉴武
 * @date 2021-04-15 03:08:01
 */
@Repository("com.hgsoft.modules.demo.mapper.TbEtcTransactionEexitMapper")
public interface TbEtcTransactionEexitMapper extends DataMapper<MerchantAccountReport> {


    /**
     * 分页查询数据
     * @param iPage
     * @param merchantAccountReport
     * @param statisticsDimension
     * @return
     */
    IPage<MerchantAccountReport> findPage(IPage<MerchantAccountReport> iPage, @Param("entity") MerchantAccountReport merchantAccountReport,@Param("statisticsDimension") String statisticsDimension);

    /**
     * 根据条件查询汇总信息
     * @param merchantAccountReport
     * @return
     */
    MerchantAccountReport getSum(@Param("entity") MerchantAccountReport merchantAccountReport,@Param("statisticsDimension") String statisticsDimension);

    /**
     * 根据条件查询数据
     * @param merchantAccountReport
     * @return
     */
    List<MerchantAccountReport> findListByCondition(@Param("entity") MerchantAccountReport merchantAccountReport,@Param("statisticsDimension") String statisticsDimension);
}