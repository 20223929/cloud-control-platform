package com.hgsoft.modules.gdetc.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.gdetc.entity.GdetcProfit;
import com.hgsoft.modules.gdetc.entity.QueryCondition;
import com.hgsoft.modules.report.entity.PageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 联合电服收益mapper
 * Created by 吴鉴武
 * date:2022/5/6 13:49
 */
@Repository
public interface GdetcProfitMapper extends DataMapper<GdetcProfit> {

    /**
     * 按条件分页查询联合电服收益信息
     * @param page
     * @param condition
     * @return
     */
    PageVo<GdetcProfit> findPageByCondition(PageVo<GdetcProfit> page, @Param("condition") QueryCondition condition);

    /**
     * 按条件查询联合电服列表信息
     * @param condition
     * @return
     */
    List<GdetcProfit> findListByCondition(@Param("condition") QueryCondition condition);

    /**
     * 查询当前条件下的汇总数据
     * @param condition
     * @return
     */
    GdetcProfit findSum(@Param("condition") QueryCondition condition);
}
