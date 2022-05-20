package com.hgsoft.modules.report.entity;

import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 吴鉴武 on 2021/4/16 15:19
 */
public class PageVo<T> extends Page<T> {
    /**
     * 附带非列表数据
     */
    private Map<String,Object> dataMap = new HashMap<>();

    public Map<String,Object> getDataMap(){
        return this.dataMap;
    }
}
