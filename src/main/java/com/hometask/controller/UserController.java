package com.hometask.controller;

import com.hometask.model.Role;
import com.hometask.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Controller
public class UserController {
    private UserService serv;

    public UserController(UserService serv) {
        this.serv = serv;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView home(Authentication authentication, ModelAndView model) {
        model.addObject("user", serv.getUserByEmail(authentication.getName()));
        model.setViewName("user-page");
        return model;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String allUsers(ModelAndView modelAndView) {
       // List<User> allUser = serv.getAllUsers();
       // modelAndView.setViewName("admin-page");
      //  modelAndView.addObject("users", allUser);
        return "admin-page";
    }

//    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
//    public String addUser(@ModelAttribute("user") User user, @RequestParam("role") String role) {
//        Set<Role> roles = user.getRoles();
//        if (role.contains("user")) {
//            roles.add(Role.user);
//        }
//        if (role.contains("admin")) {
//            roles.add(Role.admin);
//        }
//        user.setRoles(roles);
//        serv.addUser(user);
//        return "redirect:/admin";
//    }

//    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
//    public String updateUser(@ModelAttribute("user") User user,@RequestParam("role") String role) {
//        Set<Role> roles = user.getRoles();
//        if (role.contains("user")) {
//            roles.add(Role.user);
//        }
//        if (role.contains("admin")) {
//            roles.add(Role.admin);
//        }
//        user.setRoles(roles);
//        serv.updateUser(user);
//        return "redirect:/admin";
//    }

    @RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("id") Long id) {
        serv.deleteUser(serv.getUserById(id));
        return "redirect:/admin";
    }

    @RequestMapping("/login")
    public ModelAndView getLogin(Authentication authentication, HttpServletRequest request, ModelAndView model, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (authentication != null) {
            if (authentication.getAuthorities().contains(Role.admin)) {
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

}
