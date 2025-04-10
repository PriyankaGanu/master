package com.example.AutomationDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController1 {
   
    @GetMapping("/getStringData1")
    public String getStringData1()
    {
        System.out.println("function getStringData1 called...");
        return "Welcome to Automation Demo !!";
    }

    @GetMapping("/getStringData2")
    public String getStringData2()
    {
        System.out.println("function getStringData2 called...");
        return "Welcome to Automation Demo !!";
    }

}
