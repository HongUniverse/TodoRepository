package com.example.uiju;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallHello {
    @GetMapping("/hello")
    public DemoModel hello(@RequestParam String name){
        DemoModel obj = DemoModel.builder().name(name).build();
        return obj;
    }

}
