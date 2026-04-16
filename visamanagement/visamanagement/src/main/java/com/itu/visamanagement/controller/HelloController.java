package com.itu.visamanagement.controller;

import com.itu.visamanagement.services.HelloService;

import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping
    public String sayHello(Model model) {
        String hello = helloService.getHelloMessage();
        model.addAttribute("hello", hello);
        return "Hello";
    }
}
