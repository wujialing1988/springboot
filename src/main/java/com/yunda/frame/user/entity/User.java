package com.yunda.frame.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wujl
 * @since 2019-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_USER")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "登录账号")
    @TableField("U_ID")
    private String uId;

    @ApiModelProperty(value = "用户姓名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "登录密码")
    @TableField("PASSWORD")
    private String password;

    @ApiModelProperty(value = "邮箱地址")
    @TableField("EMAIL")
    private String email;

    @ApiModelProperty(value = "电话号码")
    @TableField("PHONE_NUM")
    private String phoneNum;

    @ApiModelProperty(value = "昵称")
    @TableField("NIK_NAME")
    private String nikName;


}
