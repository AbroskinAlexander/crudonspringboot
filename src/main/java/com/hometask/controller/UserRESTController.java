package com.hometask.controller;

import com.hometask.model.Role;
import com.hometask.model.User;
import com.hometask.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class UserRESTController {

    private UserService userService;

    public UserRESTController(UserService userService) {
        this.userService = userService;
    }
    @CrossOrigin
    @RequestMapping(value = "/admin/alluser", method = RequestMethod.GET)
    public List<User> getSomeInf(){
        return userService.getAllUsers();

    }
    @CrossOrigin
    @RequestMapping(value = "/admin/userup", method = RequestMethod.POST)
    public void updateUser(@ModelAttribute("user") User user, @RequestParam("role") String role){
        Set<Role> roles = user.getRoles();
        if (role.contains("user")) {
            roles.add(Role.user);
        }
        if (role.contains("admin")) {
            roles.add(Role.admin);
        }
        user.setRoles(roles);
        userService.updateUser(user);
    }
    @CrossOrigin
    @RequestMapping(value = "/admin/useradd", method = RequestMethod.POST)
    public void addUser(@ModelAttribute("user") User user, @RequestParam("role") String role){
        Set<Role> roles = user.getRoles();
        if (role.contains("user")) {
            roles.add(Role.user);
        }
        if (role.contains("admin")) {
            roles.add(Role.admin);
        }
        user.setRoles(roles);
        userService.addUser(user);
    }

}
