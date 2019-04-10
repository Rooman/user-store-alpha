package com.anabol.userstore.web.controller;

import com.anabol.userstore.entity.User;
import com.anabol.userstore.service.UserService;
import com.anabol.userstore.web.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public void getUsers(HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("users", userService.findAll());
        response.getWriter().println(PageGenerator.instance().getPage("users.html", pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @GetMapping("/users/add")
    public void getAddPage(HttpServletResponse response) throws IOException {
        response.getWriter().println(PageGenerator.instance().getPage("add.html", null));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam double salary) {

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setSalary(salary);
        user.setDateOfBirth(LocalDate.now());

        userService.insert(user);

        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    @ResponseBody
    public String editUser(@PathVariable int id) {

        User user = userService.findById(id);
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("user", user);

        String page = PageGenerator.instance().getPage("edit.html", pageVariables);

        return page;
    }
}
