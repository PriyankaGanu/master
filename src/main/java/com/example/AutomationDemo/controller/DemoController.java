package com.example.AutomationDemo.controller;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("/getStringData/{id}")
    public String getStringData(@RequestParam String id)
    {
        LOGGER.info("function getStringData called...");
        if(id.equals("1"))
        {
            return "Welcome to Automation Demo 1 !!";
        }else if(id.equals("2")){
            return "Welcome to Automation Demo 2 !!";
        } else if(id.equals("3")){
            return "Welcome to Automation Demo 3 !!";
        }else if(id.equals("4")){
            return "Welcome to Automation Demo 4 !!";
        }else if(id.equals("5")){
            return "Welcome to Automation Demo 5 !!";
        }else if(id.equals("6")){
            return "Welcome to Automation Demo 6 !!";
        }else {
            return "Welcome to Automation Demo !!";
        }
    }

}
