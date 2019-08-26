package com.sq.sell.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * redis实现分布式锁测试通过
 * 存在并发条件下时间覆盖的问题
 * 应该尝试使用原子方法，set(k,v,l,time);等等来实现这一部分有待完善
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
            String oldValue = stringRedisTemplate.opsForValue().get(key);

            if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue))
            {

                stringRedisTemplate.opsForValue().set(key,value);
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
