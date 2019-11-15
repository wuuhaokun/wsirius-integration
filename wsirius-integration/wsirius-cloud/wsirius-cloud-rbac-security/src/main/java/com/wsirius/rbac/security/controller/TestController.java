package com.wsirius.rbac.security.controller;

import com.wsirius.core.base.Result;
import com.wsirius.core.util.Results;
//import com.wsirius.rbac.security.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 测试Controller
 * </p>
 *
 * @package: com.xkcoding.rbac.security.controller
 * @description: 测试Controller
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 15:44
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public Result list() {
        log.info("测试列表查询");
        return Results.success("测试列表查询");
        //return ApiResponse.ofMessage("测试列表查询");
    }

    @PostMapping
    public Result add() {
        log.info("测试列表添加");
        //return ApiResponse.ofMessage("测试列表添加");
        return Results.success("测试列表添加");
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id) {
        log.info("测试列表修改");
        return Results.success("测试列表修改");
        //return ApiResponse.ofSuccess("测试列表修改");
    }
}
