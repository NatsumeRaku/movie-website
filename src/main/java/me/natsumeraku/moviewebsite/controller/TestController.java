package me.natsumeraku.moviewebsite.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/")
    public String home() {
        return "Hello! Movie Website is running!";
    }
    
    @GetMapping("/test")
    public String test() {
        return "Test endpoint is working!";
    }
}