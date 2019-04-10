package com.anabol.userstore.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component -> @Repository, @Service, @Controller, @RestController, @Configuration
@Controller
public class HelloController {

    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    public void hello(HttpServletResponse response) throws IOException {
        response.getWriter().write("Hello world");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/param")
    public void paramTest(HttpServletResponse response, @RequestParam("id") int id) throws IOException {
        response.getWriter().write("Request param: " + id);
    }
}
