package com.hgsoft.modules.querymanage.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.querymanage.entity.TbOnlineRefundEexit;
import com.hgsoft.modules.report.entity.PageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 银行退费流水查询MAPPER接口
 *
 * @author 雷新辉
 * @version 2021-04-22 09:25:22
 */
@Repository("com.hgsoft.modules.querymanage.mapper.TbOnlineRefundEexitMapper")
public interface TbOnlineRefundEexitMapper extends DataMapper<TbOnlineRefundEexit> {

    PageVo<TbOnlineRefundEexit> findPage(IPage<TbOnlineRefundEexit> page, @Param("entity") TbOnlineRefundEexit tbOnlineRefundEexit);
}