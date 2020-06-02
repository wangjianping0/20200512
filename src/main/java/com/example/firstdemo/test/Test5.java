package com.example.firstdemo.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@CommonsLog
public class Test5 {
    public static void main(String[] args) {
        log.info("---------------start-----------------");
        ReentrantLock reentrantLock=new ReentrantLock();

        log.info("----------------end----------------");
    }
}
