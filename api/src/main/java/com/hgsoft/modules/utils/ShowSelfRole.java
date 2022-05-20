package com.hgsoft.modules.utils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 用于能够在前台展示自己的角色
 */
public interface ShowSelfRole {

    default String movePointToLeft2(Long fee) {
        if (fee == null)
            return "0.00";
        BigDecimal _temp = new BigDecimal(fee);
        return _temp.movePointLeft(2).toString();
    }

    default String roundHalfUpScale2(BigDecimal fee){
        if (fee == null)
            return "0.00";
        return  fee.movePointLeft(2).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }
    /**
     * 将自己变成可以展示的状态
     * @param merchantInfo
     */
    public void triggerShowState(Map<String, String> merchantInfo);

}
