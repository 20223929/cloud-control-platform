package com.hgsoft.modules.bankbillcheck.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Created by 吴鉴武 on 2021/7/20 16:21
 */
@Data
@Builder
public class ResponseEntity {
    private Integer code;
    private String msg;
}
