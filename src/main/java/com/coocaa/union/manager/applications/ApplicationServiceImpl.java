package com.coocaa.union.manager.applications;

import com.alibaba.fastjson.JSON;
import com.coocaa.union.manager.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ApplicationServiceImpl extends BaseServiceImpl<Application, Integer> implements ApplicationService {

    private static final String APPLICATION_KEY_PREFIX = "application:";
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ApplicationRepository repository;

    @Override
    protected CrudRepository<Application, Integer> getCrudRepostory() {
        return repository;
    }

    @Override
    public Application findByAppKey(String appKey) {
        String key = APPLICATION_KEY_PREFIX + appKey;
        String clientInfo = redisTemplate.opsForValue().get(key);
        Application application = null;
        if(StringUtils.isNotBlank(clientInfo)) {
            application = JSON.parseObject(clientInfo, Application.class);
        }
        if(null == application) {
            application = repository.findByAppKey(appKey).orElse( null);
            if(null == application) {
                return null;
            }
            String saveInfo = JSON.toJSONString(application);
            redisTemplate.opsForValue().set(key, saveInfo, 24, TimeUnit.HOURS);
        }
        return application;
    }


    @Override
    public void save(Application item) {
        super.save(item);
        String key = APPLICATION_KEY_PREFIX + item.getAppKey();
        redisTemplate.delete(key);
    }
}
