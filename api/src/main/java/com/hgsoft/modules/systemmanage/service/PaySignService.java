package com.hgsoft.modules.systemmanage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.modules.systemmanage.entity.shanxi.PaySign;
import org.springframework.web.servlet.ModelAndView;

/**
 * 签约管理服务层
 * Created by 吴鉴武 on 2021/6/4 15:11
 */
public interface PaySignService {

    /**
     * 分页查询签约信息
     * @param page
     * @param paySign
     * @return
     */
    IPage<PaySign> findPage(IPage<PaySign> page,PaySign paySign);

    /**
     * 更新状态并刷到redis
     * @param sysId
     * @param status
     * @param vehicleNumber
     * @return
     */
    String editStatus(Long sysId,Integer status,String vehicleNumber);

    /**
     * 导出二次签约名单数据
     * @param paySign
     * @return
     */
    ModelAndView exportExcel(PaySign paySign);
}
