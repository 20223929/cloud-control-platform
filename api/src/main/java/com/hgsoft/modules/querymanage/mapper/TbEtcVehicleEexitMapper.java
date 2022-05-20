package com.hgsoft.modules.querymanage.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.modules.querymanage.entity.TbEtcVehicleEexit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.hgsoft.modules.querymanage.mapper.TbEtcVehicleEexitMapper")
public interface TbEtcVehicleEexitMapper {
    /**
     * 用于分页查询
     *
     * @param page
     * @param entity
     * @return
     */
    public IPage<TbEtcVehicleEexit> findSearchDataPage(IPage<TbEtcVehicleEexit> page, @Param("entity") TbEtcVehicleEexit entity);

    /**
     * 用于导出
     *
     * @param entity
     * @return
     */
    public List<TbEtcVehicleEexit> findSearchDataAll(@Param("entity") TbEtcVehicleEexit entity);
}
