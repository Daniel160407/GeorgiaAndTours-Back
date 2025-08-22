package com.georgiaandtours.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class PageController {

    @GetMapping(value = { "", "/home", "/contact", "/adminpanel/home", "/adminpanel/contact"})
    public Resource getPage() {
        return new ClassPathResource("/static/index.html");
    }
}
