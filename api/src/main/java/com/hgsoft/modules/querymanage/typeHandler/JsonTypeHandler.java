package com.hgsoft.modules.querymanage.typeHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.hgsoft.modules.querymanage.entity.shanxi.GasStationDetailInfo;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis查询类型转换器(json字符串转对象)
 * Created by 吴鉴武 on 2021/5/6 17:21
 */
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {

    private static final ObjectMapper mapper = new ObjectMapper();
    private Class<T> clazz;
    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public JsonTypeHandler(Class<T> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, T parameter, JdbcType jdbcType) throws SQLException {
        try {
            preparedStatement.setString(i, mapper.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化对象失败",e);
        }
    }

    @Override
    public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return this.toBean(resultSet.getString(s), clazz);
    }

    @Override
    public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return this.toBean(resultSet.getString(i), clazz);
    }

    @Override
    public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return this.toBean(callableStatement.getString(i), clazz);
    }

    private T toBean(String content, Class<?> clazz) {
        if (content != null && !content.isEmpty()) {
            try {
                return (T) mapper.readValue(content, clazz);
            } catch (Exception e) {
                throw new RuntimeException("json字符串转对象失败",e);
            }
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        String str = "{\"operator_id\":\"123456\"}";
        GasStationDetailInfo detail = objectMapper.readValue(str,GasStationDetailInfo.class);
        System.out.println(detail.getOperatorId());
    }
}
