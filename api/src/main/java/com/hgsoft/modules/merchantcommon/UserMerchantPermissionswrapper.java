package com.hgsoft.modules.merchantcommon;

import cn.hutool.core.util.StrUtil;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class UserMerchantPermissionswrapper {

    @Autowired
    private MerchantManageService merchantManageService;

    @Data
    public class UserMerchantParam {
        private Integer nodeLevel;
        private List<String> searchIds;
    }

    /**
     * 对用户权限进行校验<br>
     * 1.校验通过后，会以传入的参数或用户本地绑定的商户信息调用回调函数。
     * 传入参数是UserMerchantParam类的实例
     *
     * @param nodeLevel
     * @param searchId
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> ResultBean<T> permissionsCheck(Integer nodeLevel, String searchId, Function<UserMerchantParam, T> callBack) {
        String userId = ShiroSecurityUtil.getPrincipal().getId();
        Boolean isSuperUser = ShiroSecurityUtil.getPrincipal().getIsSuperUser();
        String[] userMerchant = null;
        if (!isSuperUser) {
            userMerchant = merchantManageService.getUserMerchant(userId, isSuperUser);
            if (userMerchant == null) {
                return ResultHandler.error("该用户没有商户,不含任何权限没有权限");
            }
        }
        if (searchId != null && !searchId.isEmpty()) {
            switch (nodeLevel) {
                case 1:
                case 2:
                case 3:
                    break;
                default:
                    return ResultHandler.error("传入节点信息异常");
            }
            if (!isSuperUser) {
                boolean validMerchantResult = merchantManageService.validMerchant(userId, searchId);
                if (!validMerchantResult)
                    return ResultHandler.error("传入查询节点与该用户所属的商户权限不匹配");
            }
            userMerchant = new String[]{String.valueOf(nodeLevel), searchId};
            return ResultHandler.ok(callBack.apply(toParam(userMerchant)));
        } else {
            return ResultHandler.ok(callBack.apply(toParam(userMerchant)));
        }
    }

    public interface DefaultPermissionProvideConsumer {
        void accept(String merchantGroupId, String merchantId, String siteId);
    }

    /**
     * 对不需要添加默认机构的可以调用该方法
     *
     * @param merchantGroupId
     * @param merchantId
     * @param siteId
     * @param callback
     * @param <T>
     * @return
     */
    public <T> ResultBean<T> permissionsCheck(String merchantGroupId, String merchantId, String siteId,
                                              Supplier<T> callback) {
        return permissionsCheck(merchantGroupId, merchantId, siteId, (_merchantGroupId, _merchantId, _siteId) -> {
                    throw new RuntimeException("传入商户号不能是空的");//这里是因为不能给默认的商户号，所以抛异常
                },
                callback);
    }

    public <T> ResultBean<T> permissionsCheck(String merchantGroupId, String merchantId, String siteId,
                                              DefaultPermissionProvideConsumer provideConsumer,
                                              Supplier<T> callback) {
        String userId = ShiroSecurityUtil.getPrincipal().getId();
        Boolean isSuperUser = ShiroSecurityUtil.getPrincipal().getIsSuperUser();
        if (isSuperUser)
            return ResultHandler.ok(callback.get());
        if (StrUtil.isBlank(merchantGroupId) && StrUtil.isBlank(merchantId) && StrUtil.isBlank(siteId)) {
            String[] userMerchant = merchantManageService.getUserMerchant(userId, isSuperUser);
            if (userMerchant == null) {
                return ResultHandler.error("该用户没有商户,不含任何权限没有权限");
            }
            switch (userMerchant[0]) {
                case "1":
                    merchantGroupId = userMerchant[1];
                    break;
                case "2":
                    merchantId = userMerchant[1];
                    break;
                case "3":
                    siteId = userMerchant[1];
                    break;
            }
            provideConsumer.accept(merchantGroupId, merchantId, siteId);
            return ResultHandler.ok(callback.get());
        }
        Map<String, String> allMerchantByUserId = merchantManageService.getAllMerchantByUserId(userId, isSuperUser);
        if (StrUtil.isNotBlank(merchantGroupId) && !allMerchantByUserId.containsKey(merchantGroupId))
            return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对一级商户" + merchantGroupId + "的处理权限");
        if (StrUtil.isNotBlank(merchantId) && !allMerchantByUserId.containsKey(merchantId))
            return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对二级商户" + merchantId + "的处理权限");
        if (StrUtil.isNotBlank(siteId) && !allMerchantByUserId.containsKey(siteId))
            return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对三级级商户" + siteId + "的处理权限");
        return ResultHandler.ok(callback.get());
    }


    public boolean permissionsCheck(String merchantGroupId, String merchantId, String siteId) {
        String userId = ShiroSecurityUtil.getPrincipal().getId();
        Boolean isSuperUser = ShiroSecurityUtil.getPrincipal().getIsSuperUser();
        if (isSuperUser)
            return true;
        Map<String, String> allMerchantByUserId = merchantManageService.getAllMerchantByUserId(userId, isSuperUser);
        if (merchantGroupId == null && merchantId == null && siteId == null) {
            ResultHandler.error("用户传入参数不含任何商户信息");
        }
        if (merchantGroupId != null && !allMerchantByUserId.containsKey(merchantGroupId))
            return false;
        if (merchantId != null && !allMerchantByUserId.containsKey(merchantId))
            return false;
        if (siteId != null && !allMerchantByUserId.containsKey(siteId))
            return false;
        return true;
    }

    private UserMerchantParam toParam(String[] userMerchant) {
        if (userMerchant == null) return null;
        UserMerchantParam param = new UserMerchantParam();
        param.setNodeLevel(Integer.valueOf(userMerchant[0]));
        ArrayList<String> searchIds = new ArrayList<>(userMerchant.length - 1);
        for (int i = 1; i < userMerchant.length; i++) {
            searchIds.add(userMerchant[i]);
        }
        param.setSearchIds(searchIds);
        return param;
    }
}
