package com.hgsoft.modules.merchantcommon;

import cn.hutool.core.lang.Assert;
import lombok.Getter;

/**
 * Created by 吴鉴武 on 2021/4/27 8:53
 */
public enum NodeLevelEnum {
    MERCHANT_GROUP(1,"expplatformetc:merchant_group",4),
    MERCHANT(2,"expplatformetc:merchant",9),
    SITE(3,"expplatformetc:site",13),
    USER_AND_MERCHANT(4,"expplatformetc:user_and_merchant",0)
    ;
    @Getter
    private Integer code;
    @Getter
    private String redisKeyPrefix;
    @Getter
    private Integer idLength;
    @Getter
    private Class mapperClass;

    NodeLevelEnum(Integer code,String redisKeyPrefix,Integer idLength){
        this.code = code;
        this.redisKeyPrefix = redisKeyPrefix;
        this.idLength = idLength;
    }

    /**
     * 根据节点获取rediskey
     * @param code
     * @return
     */
    public static String getRedisKeyPrefixByCode(Integer code){
        Assert.notNull(code,"商户级别为空");
        String redisKeyPrefix = null;
        for(NodeLevelEnum nodeLevelEnum : NodeLevelEnum.values()){
            if(nodeLevelEnum.code.intValue() == code.intValue()){
                redisKeyPrefix =  nodeLevelEnum.redisKeyPrefix;
                break;
            }
        }
        Assert.notBlank(redisKeyPrefix,"商户级别不存在");
        return redisKeyPrefix;
    }

    /**
     * 根据id长度获取节点
     * @param idLength
     * @return
     */
    public static Integer getNodeLevelByIdLength(Integer idLength){
        Assert.notNull(idLength,"id为空");
        Integer nodeLevel = null;
        for(NodeLevelEnum nodeLevelEnum : NodeLevelEnum.values()){
            if(nodeLevelEnum.idLength.intValue() == idLength.intValue()){
                nodeLevel =  nodeLevelEnum.code;
                break;
            }
        }
        Assert.notNull(nodeLevel,"id非法");
        return nodeLevel;
    }

    /**
     * 根据节点获取id长度
     * @param nodeLevel
     * @return
     */
    public static Integer getIdLengthByNodeLevel(Integer nodeLevel){
        Assert.notNull(nodeLevel,"节点为空");
        Integer idLength = null;
        for(NodeLevelEnum nodeLevelEnum : NodeLevelEnum.values()){
            if(nodeLevelEnum.getCode().intValue() == nodeLevel.intValue()){
                idLength = nodeLevelEnum.idLength;
                break;
            }
        }
        Assert.notNull(idLength,"nodeLevel非法");
        return idLength;
    }
}
