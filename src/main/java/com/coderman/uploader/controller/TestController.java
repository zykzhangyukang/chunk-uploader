package com.coderman.uploader.controller;

import com.coderman.uploader.response.RestApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhangyukang
 * @Date 2021/1/16 15:14
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping(value = "/error")
    public RestApiResponse<String> error(){
        return RestApiResponse.success();
    }
}
