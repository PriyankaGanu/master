package com.example.AutomationDemo.controller;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController1.class);


    @GetMapping("/getStringData1")
    public String getStringData1()
    {
        LOGGER.info("function getStringData1 called...");
        return "Welcome to Automation Demo !!";
    }

    @GetMapping("/getStringData2")
    public String getStringData2()
    {
        LOGGER.info("function getStringData2 called...");
        return "Welcome to Automation Demo !!";
    }

}
