package com.hgsoft.modules.utils;

import java.math.BigDecimal;

public class FeeUtil {
    public static final String movePointToLeft2(Long fee) {
        if (fee == null)
            return "0.00";
        BigDecimal _temp = new BigDecimal(fee);
        return _temp.movePointLeft(2).toString();
    }
}
