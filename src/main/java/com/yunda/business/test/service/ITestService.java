package com.yunda.business.test.service;

import com.yunda.business.test.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统测试 服务类
 * </p>
 *
 * @author wujl
 * @since 2019-05-21
 */
public interface ITestService extends IService<Test> {

    void doManyTimes(int times);
}
