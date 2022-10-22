package com.example.maynard.tradition.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {

    @GetMapping
    public String index() {
        return "hello";
    }
}
