package org.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogController {

    private final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    private String home(){
        LOGGER.debug("Inside the home method");
        return "Hitting the API!";
    }

}
