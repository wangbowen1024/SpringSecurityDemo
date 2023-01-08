package com.securitydemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/hello2")
    @PreAuthorize("hasAuthority('hello2')")
    public String hello2() {
        return "hello2";
    }

    @RequestMapping("/hello3")
    @PreAuthorize("hasRole('r1')")
    public String hello3() {
        return "hello3";
    }

    @RequestMapping("/hello4")
    @PreAuthorize("hasAuthority('hello4')")
    public String hello4() {
        return "hello4";
    }

    @RequestMapping("/hello5")
    @PreAuthorize("hasRole('r2')")
    public String hello5() {
        return "hello5";
    }
}