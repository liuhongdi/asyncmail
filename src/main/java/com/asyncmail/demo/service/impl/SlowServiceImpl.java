package com.asyncmail.demo.service.impl;

import com.asyncmail.demo.service.SlowService;
import com.asyncmail.demo.util.TimeUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.Future;


@Service
public class SlowServiceImpl implements SlowService {

    private static Logger log= LoggerFactory.getLogger(SlowServiceImpl.class);

    //sleep 1秒
    @Override
    public void sleepawhile(){
        long startTime = System.currentTimeMillis();
        log.info("function sleep begin");
        try {
            Thread.sleep(1000);    //延时1秒
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        log.info("function sleep   end");
    }

    //sleep 1秒，异步执行，并返回一个统计用的字串
    @Async
    @Override
    public Future<String> asyncsleepawhile(int i){
        log.info("async function sleep begin");
        String start=TimeUtil.getMilliTimeNow();
        try {
            Thread.sleep(1000);    //延时1秒
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        log.info("async function sleep   end");
        String end=TimeUtil.getMilliTimeNow();
        return new AsyncResult<>(String.format("第{%s}个异步调用asyncsleepawhile方法:开始时间:%s,结束时间:%s", i,start,end));
    }
}
