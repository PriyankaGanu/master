package com.example.AutomationDemo.controller;

import com.example.AutomationDemo.model.AutomationData;
import com.example.AutomationDemo.service.SystemOutReplacerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemOutReplacerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemOutReplacerController.class);

    @Autowired
    private SystemOutReplacerService replacerService;

//    @GetMapping("/replace-system-out")
//    public String replaceSystemOut() {
//        replacerService.replaceSystemOut();
//        return "System.out.println replacement process completed!";
//    }

    @PostMapping("/replace-system-out")
    public String replaceSystemOut(@RequestBody AutomationData automationData) {
        replacerService.replaceSystemOut(automationData);
        return "System.out.println replacement process completed!";
    }


}
