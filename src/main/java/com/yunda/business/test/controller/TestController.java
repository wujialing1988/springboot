package com.yunda.business.test.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunda.business.test.entity.Test;
import com.yunda.business.test.service.ITestService;
import com.yunda.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 系统测试 前端控制器
 * </p>
 *
 * @author wujl
 * @since 2019-05-21
 */
@RestController
@RequestMapping("/test/test")
@Slf4j
public class TestController {

    @Resource
    private ITestService testService ;

    /**
     * 添加
     * @param test 实体
     * @return response
     */
    @RequestMapping("/add")
    public Response add(@RequestBody @Valid Test test){
        Response response = new Response();
        testService.save(test);
        return response;
    }

    /**
     * 修改
     * @param test 实体
     * @return response
     */
    @RequestMapping("/update")
    public Response update(@RequestBody @Valid Test test){
        Response response = new Response();
        UpdateWrapper<Test> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",test.getId());
        testService.update(test,wrapper);
        return response;
    }

    /**
     * 删除
     * @param id 主键
     * @return response
     */
    @RequestMapping("/delete")
    public Response delete(@RequestParam(name = "id") @NotNull String id){
        Response response = new Response();
        QueryWrapper<Test> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        testService.remove(wrapper);
        return response;
    }

    /**
     * 通过id获取实体
     * @param id 主键
     * @return response
     */
    @RequestMapping("/getEntityById")
    public Response getEntityById(@RequestParam(name = "id") @NotNull String id){
        QueryWrapper<Test> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        Test data = testService.getById(id);
        return new Response(data);
    }

    /**
     * 返回数据列表
     * @param query 模糊查询
     * @return response
     */
    @RequestMapping("/list")
    public Response list(@RequestParam(name = "query",required = false) String query){
        QueryWrapper<Test> wrapper = new QueryWrapper<>();
        List<Test> list = testService.list(wrapper);
        return new Response(list);
    }

    /**
     * 分页查询
     *
     * @param query 过滤条件
     * @param current 当前页
     * @param size 每页数量
     * @return response
     */
    @RequestMapping("/page")
    public Response page(@RequestParam(name = "query",required = false) String query,
                         @RequestParam(name = "current") int current,
                         @RequestParam(name = "size") int size){
        Response response = new Response();
        Page<Test> page = new Page<>(current,size);
        QueryWrapper<Test> wrapper = new QueryWrapper<>();
        Page<Test> result = (Page<Test>)testService.page(page,wrapper);
        response.setData(result);
        return response;
    }

    /**
     * 并发测试
     * @param times 执行次数
     * @return
     */
    @RequestMapping("/doManyTimes")
    public Response doManyTimes(@RequestParam(name = "times") int times){
        Response response = new Response();
        testService.doManyTimes(times);
        return response;
    }

}
