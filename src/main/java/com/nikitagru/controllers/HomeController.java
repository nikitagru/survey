package com.nikitagru.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

    @GetMapping("/editor")
    public String editor() {
        return "editor";
    }
}
