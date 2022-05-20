package com.hgsoft.modules.merchant.vo;

import com.hgsoft.ecip.api.vo.SysrUserVo;
import lombok.Data;

import java.util.List;

@Data
public class EtcUserVo extends SysrUserVo {
    private static final long serialVersionUID = 4254434809720719358L;

    private List<String> merchantIds;

    private String merchantName;

    private Integer nodeLevel;
}
