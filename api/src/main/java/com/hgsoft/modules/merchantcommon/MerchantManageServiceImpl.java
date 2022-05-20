package com.hgsoft.modules.merchantcommon;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.hgsoft.modules.merchant.mapper.MerchantGroupMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商户信息管理服务
 * Created by 吴鉴武 on 2021/4/26 17:47
 */
@Component
public class MerchantManageServiceImpl implements MerchantManageService {

    @Qualifier("businessRedisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MerchantGroupMapper merchantGroupMapper;

    /**
     * 获取商户树信息
     *
     * @param userId  用户ID
     * @param isAdmin 是否是超级管理员
     * @return
     */
    public List<MerchantTree> getMerchantTree(String userId, boolean isAdmin) {
        Assert.notBlank(userId, "请登录!");
        List<MerchantTree> result = new ArrayList<>();
        if (isAdmin) {
            //从数据库获取所有一级商户的ID
            List<String> rootIds = merchantGroupMapper.getIds();
            Iterator<String> iterator = rootIds.iterator();
            //同一级别的商户公用一个boundHashOperations，所以在外部初始化传入，防止内部递归创建多个对象
            BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.getRedisKeyPrefixByCode(NodeLevelEnum.MERCHANT_GROUP.getCode()));
            //循环一级商户编号，递归构建每个一级商户的树形结构
            while (iterator.hasNext()) {
                result.add(buildTree(iterator.next(), NodeLevelEnum.MERCHANT_GROUP.getCode().intValue(), boundHashOperations));
            }
            return result;
        }
        //验证用户是否由权限或权限是否合法（可能人为改了redis导致格式非法）
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.USER_AND_MERCHANT.getRedisKeyPrefix());
        String userMerchantInfo = boundHashOperations.get(userId);
        Assert.notBlank(userMerchantInfo, "用户没有商户权限");
        String[] split = StringUtils.split(userMerchantInfo, ",");
        Assert.isTrue(split.length > 1, "用户商户权限不合法");
        //同一级别的商户公用一个boundHashOperations，所以在外部初始化传入，防止内部递归创建多个对象
        int nodeLevel = Integer.parseInt(split[0]);
        BoundHashOperations nodeLevelBoundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.getRedisKeyPrefixByCode(nodeLevel));
        //一个非超级管理员用户只会有一个级别的商户权限（一级、二级或者三级）
        //循环这一级别的每一个商户编号，递归构建每个商户的树形结构
        for (int i = 1; i < split.length; i++) {
            result.add(buildTree(split[i], nodeLevel, nodeLevelBoundHashOperations));
        }
        return result;
    }

    /**
     * 验证用户商户权限是否合法
     *
     * @param userId 用户ID
     * @param id     商户ID
     * @return
     */
    public boolean validMerchant(String userId, String id) {
        //非空校验
        Assert.isTrue(StrUtil.isAllNotBlank(userId, id), "userId、id皆不能为空");
        //根据商户编号长度获取商户级别
        Integer nodeLevel = NodeLevelEnum.getNodeLevelByIdLength(id.length());
        //根据用户编号获取用户商户权限
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.USER_AND_MERCHANT.getRedisKeyPrefix());
        String userMerchantInfo = boundHashOperations.get(userId);
        //如果为空则用户没有权限
        Assert.notBlank(userMerchantInfo, "用户没有商户权限");
        String[] split = StringUtils.split(userMerchantInfo, ",");
        //用户商户权限格式非法（可能是人为插入的或人为修改的，程序插入的不会出现这种情况）
        Assert.isTrue(split.length > 1, "用户商户权限不合法");
        String[] ids = new String[split.length - 1];
        ArrayUtil.copy(split, 1, ids, 0, ids.length);
        //校验用户传入的商户是否在用户商户权限范围内（遍历节点和对应子节点对比，存在即合法，不存在即不合法）
        return validMerchant(id, nodeLevel, Integer.parseInt(split[0]), Arrays.asList(ids));
    }

    /**
     * 验证用户商户权限是否合法
     *
     * @param id              商户ID
     * @param originNodeLevel 传进来的商户节点
     * @param targetNodeLevel 查询出来的商户节点
     * @return
     */
    private boolean validMerchant(String id, Integer originNodeLevel, Integer targetNodeLevel, List<String> ids) {
        //如果传入的级别小于用户拥有的商户权限级别，则可以肯定用户没有商户权限（因为拥有二级商户权限的用户不可能拥有一级商户的权限，同一个用户同时只能拥有一个级别的商户权限）
        Assert.isFalse(originNodeLevel < targetNodeLevel, "用户没有该商户权限");
        //如果传入的级别等于用户拥有的商户权限级别，则判断当前级别对应的用户商户权限是否包含传入的商户编号，是则合法，否则非法
        if (originNodeLevel.intValue() == targetNodeLevel) return ids.contains(id);
        //如果传入的节点级别大于用户权限级别且传入的商户编号没有以用户权限编号列表中的任何一个编号为起始字符串，那么该用户不具备该权限
        Optional<String> matcherOptional = ids.stream().filter(vo -> id.startsWith(vo)).findFirst();
        if (!matcherOptional.isPresent()) return false;
        //如果如果传入的级别大于用户拥有的商户权限级别，则要迭代到同一级别，然后对比同一级别中的节点是否包含传进来的商户编号，是则合法，否则非法
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.getRedisKeyPrefixByCode(targetNodeLevel));
        targetNodeLevel = targetNodeLevel + 1;
        String matcher = matcherOptional.get();
        List<String> newIdList;
        //当下一级别仍小于传入的级别，那么要清空当前级别的节点信息，然后保存当前节点对应子节点的节点信息
        //退出循环的条件是下一级别等于传入的级别，这时迭代器中保存的是传入级别的上一级别的节点信息
        while (targetNodeLevel < originNodeLevel) {
            newIdList = new ArrayList<>();
            String childrenKey = matcher + ":children";
            if (!boundHashOperations.hasKey(childrenKey)) return false;
            String children = boundHashOperations.get(childrenKey);
            matcherOptional = Arrays.asList(StringUtils.split(children, ",")).stream().filter(vo -> id.startsWith(vo)).findFirst();
            if (!matcherOptional.isPresent()) return false;
            matcher = matcherOptional.get();
            boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.getRedisKeyPrefixByCode(targetNodeLevel));
            targetNodeLevel++;
        }
        //判断当前节点的子节点是否包含传入的商户编号，有则合法，否则非法
        String childrenKey = matcher + ":children";
        if (!boundHashOperations.hasKey(childrenKey)) return false;
        String children = boundHashOperations.get(childrenKey);
        return Arrays.asList(StringUtils.split(children, ",")).contains(id);
    }

    /**
     * 根据给定的编号获取当前节点以及对应子节点的所有商户信息(id,name)
     *
     * @param ids     商户ID列表
     * @param isAdmin 是否是超级管理员
     * @return
     */
    public Map<String, String> getAllMerchant(List<String> ids, boolean isAdmin) {
        Assert.isTrue(isAdmin || CollUtil.isNotEmpty(ids), "非超级管理员的商户ID不能为空");
        List<MerchantTree> list = new ArrayList<>();
        if (isAdmin && CollUtil.isEmpty(ids)) {
            List<String> rootIds = merchantGroupMapper.getIds();
            Iterator<String> iterator = rootIds.iterator();
            //同一级共用一个boundHashOperations，在外部创建避免创建多个对象
            BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.getRedisKeyPrefixByCode(NodeLevelEnum.MERCHANT_GROUP.getCode()));
            //如果时超级管理元且从一级开始，那么要从上往下遍历构建所有商户信息的编号名称映射列表
            while (iterator.hasNext()) {
                getAllMerchantInfo(iterator.next(), NodeLevelEnum.MERCHANT_GROUP.getCode(), list, boundHashOperations);
            }
            return list.stream().collect(Collectors.toMap(k -> k.getId(), v -> v.getName()));
        }
        //如果ids不为空，则从传进来的商户级别向下开始遍历构建商户信息的编号名称映射列表
        Iterator<String> iterator = ids.iterator();
        while (iterator.hasNext()) {
            String id = iterator.next();
            Integer nodeLevel = NodeLevelEnum.getNodeLevelByIdLength(id.length());
            BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.getRedisKeyPrefixByCode(nodeLevel));
            getAllMerchantInfo(id, nodeLevel, list, boundHashOperations);
        }
        return list.stream().collect(Collectors.toMap(k -> k.getId(), v -> v.getName()));
    }

    @Override
    public Map<String, String> getAllMerchantByUserId(String userId, boolean isAdmin) {
        String[] userMerchant = getUserMerchant(userId, isAdmin);
        Map<String, String> merchantInfo = null;
        if (userMerchant != null) {
            if (userMerchant.length == 2)
                merchantInfo = getAllMerchant(Arrays.asList(userMerchant[1]), isAdmin);
            else {
                merchantInfo = new HashMap<>();
                for (int i = 1; i < userMerchant.length; i++)
                    merchantInfo.putAll(getAllMerchant(Arrays.asList(userMerchant[i]), isAdmin));
            }
        } else {
            merchantInfo = new HashMap<>();
        }
        return merchantInfo;
    }

    @Override
    public String[] getUserMerchant(String userId, boolean isAdmin) {
        String[] result;
        if (isAdmin) {
            List<String> rootIds = merchantGroupMapper.getIds();
            if (rootIds == null || rootIds.isEmpty())
                return null;
            result = new String[rootIds.size() + 1];
            int i = 0;
            result[i] = "1";
            for (String rootId : rootIds) {
                result[++i] = rootId;
            }
        } else {
            BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.USER_AND_MERCHANT.getRedisKeyPrefix());
            String userMerchantInfo = boundHashOperations.get(userId);
            Assert.notBlank(userMerchantInfo, "用户没有商户权限");
            result = StringUtils.split(userMerchantInfo, ",");
            Assert.isTrue(result.length > 1, "用户商户权限不合法");
        }
        return result;
    }

    /**
     * 根据商户ID构建树
     *
     * @param id 商户ID
     * @return
     */
    private MerchantTree buildTree(String id, int nodeLevel, BoundHashOperations<String, String, String> boundHashOperations) {
        //判断商户对应的名称在redis上是否有缓存，如果没有则抛异常（因为前端展示下拉款一定要有名称）
        String name = boundHashOperations.get(id + ":name");
        Assert.notBlank(name, "商户id{}对应的名称为空", id);
        //构建树形结构（编号、级别、名称、子节点（子节点又是一个树形结构，以此类推直到最后一级））
        MerchantTree merchantTree = new MerchantTree();
        merchantTree.setId(id);
        merchantTree.setNodeLevel(nodeLevel);
        merchantTree.setName(name);
        String childrenIds = boundHashOperations.get(id + ":children");
        //如果当前节点有子节点才需要继续往下走
        if (StrUtil.isNotBlank(childrenIds)) {
            int nextNodeLevel = nodeLevel + 1;
            List<MerchantTree> children = new ArrayList<>();
            //同一级别公用一个nextLevelBoundHashOps，所以从外部传入，避免创建多个对象
            boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.getRedisKeyPrefixByCode(nextNodeLevel));
            if (nextNodeLevel == NodeLevelEnum.SITE.getCode().intValue()) {
                //如果当前节点的子节点是最后一级，则直接迭代构建节点信息即可，因为最后一级没有子节点
                for (String vo : StringUtils.split(childrenIds, ",")) {
                    String siteName = boundHashOperations.get(vo + ":name");
                    Assert.notBlank(siteName, "商户id{}对应的名称为空", vo);
                    MerchantTree merchantTreeSite = new MerchantTree();
                    merchantTreeSite.setId(vo);
                    merchantTreeSite.setNodeLevel(nextNodeLevel);
                    merchantTreeSite.setName(siteName);
                    children.add(merchantTreeSite);
                }
            } else {
                //如果当前节点的子节点不是最后一级，仍需要迭代（因为子节点可能也有自己的子节点）
                for (String vo : StringUtils.split(childrenIds, ",")) {
                    children.add(buildTree(vo, nextNodeLevel, boundHashOperations));
                }
            }
            merchantTree.setChildren(children);
        }
        return merchantTree;
    }

    /**
     * 获取商户信息及其父类商户信息以及子类商户信息
     *
     * @param id                  商户信息ID
     * @param nodeLevel           商户信息节点
     * @param list                商户信息及其父类商户信息以及子类商户信息列表
     * @param boundHashOperations 首个商户信息是否为一级商户
     */
    private void getAllMerchantInfo(String id, Integer nodeLevel, List<MerchantTree> list, BoundHashOperations<String, String, String> boundHashOperations) {
        if (nodeLevel.equals(NodeLevelEnum.MERCHANT_GROUP.getCode())) {
            //如果是从根节点开始的，则只需要从上往下遍历
            getChildMerchantInfo(id, nodeLevel, list, boundHashOperations);
        } else {
            //如果不是从根节点，则需要先往上遍历构建商户信息、再往下遍历构造商户信息
            getParentMerchantInfo(id, nodeLevel, list, boundHashOperations);
            if (nodeLevel.equals(NodeLevelEnum.SITE.getCode())) return;
            String children = boundHashOperations.get(id + ":children");
            if (StrUtil.isBlank(children)) return;
            String[] split = StringUtils.split(children, ",");
            int nextNodelLevel = nodeLevel + 1;
            boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.getRedisKeyPrefixByCode(nextNodelLevel));
            for (String childrenId : split) {
                getChildMerchantInfo(childrenId, nextNodelLevel, list, boundHashOperations);
            }
        }
    }

    /**
     * 获取父商户
     *
     * @param id
     * @param nodeLevel
     * @param list
     */
    private void getParentMerchantInfo(String id, Integer nodeLevel, List<MerchantTree> list, BoundHashOperations<String, String, String> boundHashOperations) {
        while (true) {
            Assert.isTrue(boundHashOperations.hasKey(id + ":name"), "商户{}对应的商户名称不存在", id);
            String name = boundHashOperations.get(id + ":name");
            MerchantTree merchantTree = new MerchantTree();
            merchantTree.setId(id);
            merchantTree.setName(name);
            list.add(merchantTree);
            if (nodeLevel.equals(NodeLevelEnum.MERCHANT_GROUP.getCode())) return;
            nodeLevel--;
            boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.getRedisKeyPrefixByCode(nodeLevel));
            id = id.substring(0, NodeLevelEnum.getIdLengthByNodeLevel(nodeLevel));
        }
    }

    /**
     * 获取子商户
     *
     * @param id
     * @param nodeLevel
     * @param list
     */
    private void getChildMerchantInfo(String id, Integer nodeLevel, List<MerchantTree> list, BoundHashOperations<String, String, String> boundHashOperations) {
        Assert.isTrue(boundHashOperations.hasKey(id + ":name"), "商户{}对应的商户名称不存在", id);
        String name = boundHashOperations.get(id + ":name");
        MerchantTree merchantTree = new MerchantTree();
        merchantTree.setId(id);
        merchantTree.setName(name);
        list.add(merchantTree);
        String children = boundHashOperations.get(id + ":children");
        String subChildren;
        while (StrUtil.isNotBlank(children)) {
            String[] split = StringUtils.split(children, ",");
            children = "";
            nodeLevel++;
            boundHashOperations = redisTemplate.boundHashOps(NodeLevelEnum.getRedisKeyPrefixByCode(nodeLevel));
            for (String childrenId : split) {
                Assert.isTrue(boundHashOperations.hasKey(childrenId + ":name"), "商户{}对应的商户名称不存在", childrenId);
                String childName = boundHashOperations.get(childrenId + ":name");
                MerchantTree merchantTreeChild = new MerchantTree();
                merchantTreeChild.setId(childrenId);
                merchantTreeChild.setName(childName);
                list.add(merchantTreeChild);
                if (nodeLevel.compareTo(NodeLevelEnum.SITE.getCode()) < 0) {
                    subChildren = boundHashOperations.get(childrenId + ":children");
                    if (StrUtil.isNotBlank(subChildren)) children = children + subChildren + ",";
                }
            }
        }
    }
}
