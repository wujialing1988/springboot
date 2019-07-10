package com.yunda.constant;

import lombok.Data;

/**
 * @author yunda
 * @title: CodeStatusMsg
 * @projectName devicedataserver
 * @description: 状态码
 * @date 2019/5/2215:41
 */
public enum CodeStatusMsg {

    sys_error("0001","系统错误!"),
    network_error("0002","网络错误!"),
    parameter_error("0003","参数错误!"),
    parameter_user_null_error("00031","用户名和密码不能为空!");


    private String code ;

    private String msg ;

    CodeStatusMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }}
