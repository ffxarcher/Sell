package com.sq.sell.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * redis实现分布式锁测试通过
 */

@Component
public class RedisLock {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public boolean lock(String key,String value)
    {
        if(stringRedisTemplate.opsForValue().setIfAbsent(key,value))
        {
            return true;
        }
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        //过期
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis())
        {
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key,value);

            if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue))
            {
                return true;
            }
        }

        return false;

    }

    public void unlock(String key,String value)
    {
        try {
            String currentValue = stringRedisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(currentValue) && currentValue.equals(value))
            {
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
