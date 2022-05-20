package com.hgsoft.modules.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * Created by 吴鉴武 on 2021/4/28 16:25
 */
@Configuration
public class RedisConfiguration {

    static Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer;

    static {
        jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(SerializationFeature.INDENT_OUTPUT, true);
        om.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
    }

    /**
     * 框架用，业务不要用
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

    /**
     * 业务用这个
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> businessRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用 String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // hash的 key也采用 String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用 jdk
        redisTemplate.setValueSerializer(stringRedisSerializer);
        // redisTemplate.setValueSerializer(new
        // JdkSerializationRedisSerializer());
        // hash的 value序列化方式采用 jdk
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
