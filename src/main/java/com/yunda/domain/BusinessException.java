package com.yunda.domain;

import com.yunda.constant.CodeStatusMsg;

/**
 * @author yunda
 * @title: BusinessException
 * @projectName devicedataserver
 * @description: 自定义异常
 * @date 2019/5/2310:08
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 7853481752869051806L;

    private final CodeStatusMsg response ;

    public BusinessException(CodeStatusMsg response) {
        this.response = response;
    }

    public CodeStatusMsg getResponse(){
        return  response ;
    }

}
