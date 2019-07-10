package com.yunda.business.test.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunda.business.test.entity.Test;
import com.yunda.business.test.mapper.TestMapper;
import com.yunda.business.test.service.ITestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunda.frame.user.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 * 系统测试 服务实现类
 * </p>
 *
 * @author wujl
 * @since 2019-05-21
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {


    @Resource
    private ITestService testService ;

    @Override
    public void doManyTimes(int times) {
        Runnable task = new Runnable() {

            private int count = 1;

            @Override
            public void run() {
//                List<Test> list = testService.list();
//                System.out.println( new Date() + ":" + JSONObject.toJSONString(list));
                synchronized (this){
                    System.out.println(count++);
                }
            }
        };
        try {
            startTaskAllInOnce(times, task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long startTaskAllInOnce(int threadNums, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(threadNums);
        for(int i = 0; i < threadNums; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        // 使线程在此等待，当开始门打开时，一起涌入门中
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            // 将结束门减1，减到0时，就可以开启结束门了
                            endGate.countDown();
                        }
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            };
            t.start();
        }
        long startTime = System.nanoTime();
        System.out.println(startTime + " [" + Thread.currentThread() + "] All thread is ready, concurrent going...");
        // 因开启门只需一个开关，所以立马就开启开始门
        startGate.countDown();
        // 等等结束门开启
        endGate.await();
        long endTime = System.nanoTime();
        System.out.println(endTime + " [" + Thread.currentThread() + "] All thread is completed.");
        return endTime - startTime;
    }

}
