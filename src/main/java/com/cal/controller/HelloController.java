package com.cal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by swx494800 on 2019/1/6.
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String index(){
        return "Greetings from Spring Boot!";
    }
}
