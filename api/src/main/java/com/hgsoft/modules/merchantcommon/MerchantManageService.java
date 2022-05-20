package com.hgsoft.modules.merchantcommon;

import java.util.List;
import java.util.Map;

public interface MerchantManageService {
    /**
     * 获取商户树信息
     * @param userId 用户ID
     * @param isAdmin 是否是超级管理员
     * @return
     */
    public List<MerchantTree> getMerchantTree(String userId, boolean isAdmin);

    /**
     * 验证用户商户权限是否合法
     * @param userId 用户ID
     * @param id 商户ID
     * @return
     */
    public boolean validMerchant(String userId,String id);

    /**
     * 根据给定的编号获取当前节点以及对应子节点的所有商户信息(id,name)
     * @param ids 商户ID列表
     * @param isAdmin 是否是超级管理员
     * @return
     */
    public Map<String,String> getAllMerchant(List<String> ids, boolean isAdmin);

    /**
     * 根据用户的userId，得到该用户所属的所有一级二级三级商户的id与name的映射关系
     * @param userId
     * @param isAdmin
     * @return 若查询不到改用户对应所属商户的信息，则会返回空集合
     */
    public Map<String,String> getAllMerchantByUserId(String userId, boolean isAdmin);

    /**
     * 根据用户id查询该用于关联的商户信息
     * @param userId
     * @param isAdmin
     * @return
     * String[] String[0]代表商户的级别 1,2,3
     * String[1]...String[length-1]代表具体商户的id
     * 如果某个用户未绑定任何商户信息，则返回null
     */
    public String[] getUserMerchant(String userId,boolean isAdmin);
}
