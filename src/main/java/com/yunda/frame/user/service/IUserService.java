package com.yunda.frame.user.service;

import com.yunda.domain.Response;
import com.yunda.frame.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Optional;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wujl
 * @since 2019-06-03
 */
public interface IUserService extends IService<User> {

    /**
     * web端登录
     * @param entity
     * @return
     */
    public Response webLogin(User entity) ;

    /**
     * 通过用户账号查询用户信息
     * @param uid
     * @return
     */
    public Optional<User> findUserByUid(String uid);
}
