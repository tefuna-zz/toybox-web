package com.tefuna.toybox.sort.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author tefuna
 *
 */
@RestController
public class SortController {

    @RequestMapping("/sorta")
    public String sorta(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        return "sorta";
    }
}
