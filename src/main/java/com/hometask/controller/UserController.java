package com.hometask.controller;

import com.hometask.model.Role;
import com.hometask.model.User;
import com.hometask.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    private UserService serv;

    public UserController(UserService serv) {
        this.serv = serv;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView home(Authentication authentication, ModelAndView model) {
        model.addObject("user", serv.getUserByEmail(authentication.getName()));
        model.setViewName("exuser-info");
        return model;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView allUsers(ModelAndView modelAndView) {
        List<User> allUser = serv.getAllUsers();
        modelAndView.setViewName("admin-page");
        modelAndView.addObject("users", allUser);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, HttpServletRequest request) {
        Set<Role> roles = user.getRoles();
        String RoleUser = request.getParameter("role1");
        String RoleAdmin = request.getParameter("role2");
        if (RoleUser != null) {
            roles.add(Role.USER);
        }
        if (RoleAdmin != null) {
            roles.add(Role.ADMIN);
        }
        user.setRoles(roles);
        serv.addUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    public ModelAndView editPage(@RequestParam("id") Long id, ModelAndView model) {
        model.addObject("user", serv.getUserById(id));
        model.setViewName("exuser-edit");
        return model;
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user, HttpServletRequest request) {
        Set<Role> roles = user.getRoles();
        String RoleUser = request.getParameter("role1");
        String RoleAdmin = request.getParameter("role2");
        if (RoleUser != null) {
            roles.add(Role.USER);
        }
        if (RoleAdmin != null) {
            roles.add(Role.ADMIN);
        }
        user.setRoles(roles);
        serv.updateUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("id") Long id) {
        serv.deleteUser(serv.getUserById(id));
        return "redirect:/admin";
    }

    @RequestMapping("/login**")
    public ModelAndView getLogin(Authentication authentication, HttpServletRequest request, ModelAndView model, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (authentication != null) {
            if (authentication.getAuthorities().contains(Role.ADMIN)) {
                httpServletResponse.sendRedirect("/admin");
            } else {
                httpServletResponse.sendRedirect("/user");
            }
        }
        if (request.getParameterMap().containsKey("error")) {
            model.setViewName("user-login");
            model.addObject("status", "Не верный Email или Password");
            return model;
        }
        model.setViewName("user-login");
        return model;
    }

    @RequestMapping(value = "/ad", method = RequestMethod.GET)
    public ModelAndView ad(Authentication authentication, ModelAndView model) {
        List<User> allUser = serv.getAllUsers();
        model.setViewName("admin-page");
        model.addObject("users", allUser);
        return model;
    }
}
