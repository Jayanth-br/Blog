package org.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    private String home(){
        return "Hitting the API!";
    }

}
