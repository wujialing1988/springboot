package com.yunda.handle;

import com.yunda.constant.CodeStatusMsg;
import com.yunda.domain.BusinessException;
import com.yunda.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.BindException;
import java.util.List;

/**
 * @author yunda
 * @title: ExceptionHandler
 * @projectName devicedataserver
 * @description: 统一异常拦截
 * @date 2019/5/2310:16
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    /**
     * 自定义异常拦截
     * @param ex
     * @return
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public Response handleBusinessException(HttpServletRequest request , BusinessException ex){
        Response response;
        log.error("BusinessException:",ex.getResponse().getCode(),ex.getResponse().getMsg());
        response = new Response(ex.getResponse().getCode(),ex.getResponse().getMsg());
        return response;
    }

    /**
     * 所有异常拦截
     * @param ex
     * @return
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Response handleException(HttpServletRequest request, Exception ex){
        Response response = new Response(CodeStatusMsg.sys_error.getCode(),CodeStatusMsg.sys_error.getMsg());
        log.error("Exception:",ex);
        return response;
    }

    /**
     * 参数验证拦截
     * @param ex
     * @return
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleBindException(HttpServletRequest request , Throwable ex){
        Response response = new Response();
        log.error("参数错误:",ex);
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException bindException = MethodArgumentNotValidException.class.cast(ex);
            List<ObjectError> errors = bindException.getBindingResult().getAllErrors();
            response.setCode(CodeStatusMsg.parameter_error.getCode());
            response.setMsg(errors.get(0).getDefaultMessage());
        }
        return response;
    }




}
