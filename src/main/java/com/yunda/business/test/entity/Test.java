package com.yunda.business.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 系统测试
 * </p>
 *
 * @author wujl
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_TEST")
@ApiModel(value="Test对象", description="系统测试")
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    @NotNull(message = "ID不能为空")
    private String id;

    @ApiModelProperty(value = "标题")
    @TableField("TITLE")
    @Size(min = 5,max = 20,message = "标题长度为5到20")
    private String title;

    @ApiModelProperty(value = "内容")
    @TableField("CONTENT")
    @NotEmpty(message = "内容不能为空")
    private String content;


}
