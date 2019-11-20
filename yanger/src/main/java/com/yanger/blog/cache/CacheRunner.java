package com.yanger.blog.cache;

import com.yanger.blog.dao.ConstDao;
import com.yanger.blog.po.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yanger
 * @description 加载缓存的常量类
 * @date 2019/11/20
 */
@Component
@Slf4j
public class CacheRunner implements ApplicationRunner {

    private Map<String, Const> constCacheMap;

    @Autowired
    private ConstDao constDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadConstCache();
        log.info("CacheRunner加载缓存完成");
    }

    /**
     * @description 加载const常量
     * @author yanger
     * @date 2019/11/20
     * @param
     * @return void
     */
    public void loadConstCache(){
        constCacheMap = new ConcurrentHashMap<>();
        List <Const> consts = constDao.findAll();
        consts.forEach(c -> {
            constCacheMap.put(c.getCode(), c);
        });
    }

    public Map<String, Const> getConstCache(){
        if(constCacheMap == null){
            loadConstCache();
        }
        return constCacheMap;
    }

    public void reloadConstCache() {
        constCacheMap = null;
        new Thread(() -> {
            loadConstCache();
        }).start();
    }

}
