package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "User Registration App Running 🚀";
    }

    @GetMapping("/mateen")
    public String mateen() {
        return "User Registration App Running by mateen sayyed🚀";
    }

    @GetMapping("/new")
    public String newVersion() {
        return "User Registration App Running by mateen🚀 to check versioning of docker image , check rollback";
    }
}
