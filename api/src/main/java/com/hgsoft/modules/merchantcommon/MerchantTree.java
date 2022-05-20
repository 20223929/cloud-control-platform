package com.hgsoft.modules.merchantcommon;

import lombok.Data;

import java.util.List;

/**
 * Created by 吴鉴武 on 2021/4/26 17:47
 */
@Data
public class MerchantTree {

    private String id;

    private String name;

    private Integer nodeLevel;

    private List<MerchantTree> children;
}
