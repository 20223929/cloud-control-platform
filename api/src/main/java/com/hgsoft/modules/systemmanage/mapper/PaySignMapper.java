package com.hgsoft.modules.systemmanage.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.systemmanage.entity.shanxi.PaySign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 签约管理持久层
 * Created by 吴鉴武 on 2021/6/4 14:01
 */
public interface PaySignMapper extends DataMapper<PaySign> {

    /**
     *查询二签名单列表
     * @param paySign
     * @return
     */
    @Override
    List<PaySign> findList(@Param("entity") PaySign paySign);

    /**
     *查询二签名单列表(四川)
     * @param paySign
     * @return
     */
    List<PaySign> findListSC(@Param("entity") PaySign paySign);

    /**
     * 分页查询二签名单列表（四川）
     * @param iPage
     * @param paySign
     * @return
     */
    IPage<PaySign> findPageSC(IPage<PaySign> iPage,@Param("entity") PaySign paySign);

    /**
     * 更新状态
     * @param sysId
     */
    void updateStatus(@Param("sysId") Long sysId,@Param("status") Integer status);

    /**
     * 更新状态（四川）
     * @param sysId
     * @param status
     */
    void updateStatusSC(@Param("sysId") Long sysId,@Param("status") Integer status);
}
