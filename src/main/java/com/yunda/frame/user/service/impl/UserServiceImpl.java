package com.yunda.frame.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunda.constant.CodeStatusMsg;
import com.yunda.domain.Response;
import com.yunda.frame.user.entity.User;
import com.yunda.frame.user.mapper.UserMapper;
import com.yunda.frame.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wujl
 * @since 2019-06-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Response webLogin(User entity) {
        Response response = new Response();
        if (StringUtils.isEmpty(entity.getUId()) || StringUtils.isEmpty(entity.getPassword())){
            response.setCode(CodeStatusMsg.parameter_user_null_error.getCode());
            response.setMsg(CodeStatusMsg.parameter_user_null_error.getMsg());
            return response;
        }
        // 1.通过用户UID查询数据库用户
        Optional<User> optional = this.findUserByUid(entity.getUId());
        if(!optional.isPresent()){
            response.setCode(CodeStatusMsg.sys_error.getCode());
            response.setMsg("用户账号："+ entity.getUId() + "不存在！");
            return response;
        }
        User user = optional.get();
        // 2.MD5加密比较密码 TODO
        response.setData(user);
        return response;
    }

    @Override
    public Optional<User> findUserByUid(String uid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid);
        User user = this.getOne(wrapper);
        return Optional.ofNullable(user);
    }
}
