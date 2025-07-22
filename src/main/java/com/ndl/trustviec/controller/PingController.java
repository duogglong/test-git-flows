package com.ndl.trustviec.controller;

import com.ndl.trustviec.config.annotation.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(path = "/ping")
public class PingController {

    @GetMapping
    public String ping() {
        return "Common in on";
    }
}
