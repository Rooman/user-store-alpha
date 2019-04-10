package com.anabol.userstore.web.controller;

import com.anabol.userstore.entity.User;
import com.anabol.userstore.service.UserService;
import com.anabol.userstore.web.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    @Qualifier("cachedUserService")
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public String getUsers(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.findAll());
        return "users";
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
                          @RequestParam double salary,
                          @RequestParam
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                          @ModelAttribute User userFromForm) {

        System.out.println(dateOfBirth);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setSalary(salary);
        user.setDateOfBirth(LocalDate.now());

        userService.insert(userFromForm);

        return "redirect:/web/users";
    }

    @GetMapping(value = "/users/edit/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String editUser(@PathVariable int id) {

        User user = userService.findById(id);
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("user", user);

        String page = PageGenerator.instance().getPage("edit.html", pageVariables);

        return page;
    }
}
