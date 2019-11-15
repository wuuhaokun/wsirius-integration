package com.wsirius.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wsirius.core.base.Result;
import com.wsirius.core.util.Results;

/**
 * description
 *
 * @author bojiangzhou 2018/11/13
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/instances/{serviceId}")
    public Result instance(@PathVariable String serviceId) {
        List<ServiceInstance> list = discoveryClient.getInstances(serviceId);
        return Results.successWithData(list);
    }

}
