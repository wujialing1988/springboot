package com.yunda.controller.test;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * wujl
 * 测试
 */
@RestController
@RequestMapping("/test1")
@Api(value = "/test1", description = "测试API")
public class Test1Controller {

    @RequestMapping(value = {"/hello"},method = RequestMethod.GET)
    @ApiOperation(value="hello方法", notes="测试第一个方法")
    public String hello(){
        return "hello kettle!";
    }

    @GetMapping("/get/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "编号",required = true,paramType = "path",dataType = "String"),
            @ApiImplicitParam(name = "name",value = "名称",required = true,paramType = "query",dataType = "String")
    })
    @ApiOperation(value="helloParames方法", notes="测试不同参数")
    public String helloParames(@PathVariable(value = "id") String id,@RequestParam(value = "name") String name){
        return "编号："+id + "，姓名："+name;
    }

    @PostMapping("/post")
    @ApiOperation(value = "post方法",notes = "通过POST传递实体参数")
    public String postMap(@RequestBody Map<String,String> map){
        System.out.println(map);
        return "编号："+map.get("id")+",姓名："+map.get("name");
    }




}
