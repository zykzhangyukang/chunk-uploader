package com.coderman.uploader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @Author zhangyukang
 * @Date 2021/1/17 12:40
 * @Version 1.0
 **/
@SpringBootTest(classes = {UploaderApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        redisTemplate.opsForHash().put("1ae8da00bc9cd674619ffd41a382a742",
                "uploaded",new HashSet<>(Arrays.asList(1L, 2L, 3L, 4L, 5L)));
    }
}
