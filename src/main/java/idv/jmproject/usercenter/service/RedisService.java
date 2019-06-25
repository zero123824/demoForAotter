package idv.jmproject.usercenter.service;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Resource
    private RedisTemplate redisTemplate;

    public boolean setKey(String key, Long value) {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        //設定userName lock 1小時候失效
        redisTemplate.boundValueOps(key).set(value,1, TimeUnit.HOURS);
        System.out.println(redisTemplate.opsForValue().get(key));
        connection.close();
        return true;
    }

    public Long checkKey(String key, Long counter){
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        counter = redisTemplate.boundValueOps(key).get() == null ? 0L : (Long)redisTemplate.boundValueOps(key).get();
        connection.close();
        return counter;
    }

    public boolean deleteKey(String key){
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        return redisTemplate.delete(key);
    }
}
