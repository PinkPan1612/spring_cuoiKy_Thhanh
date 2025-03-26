package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;
    private RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/home")
    public String studentList() {
        return "admin";
    }

    // user list
    @GetMapping("/admin/users")
    public String list(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/show";
    }

    // user detail
    @GetMapping("/admin/users/detail/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        User user = this.userService.getUserByID(id);
        model.addAttribute("userDetail", user);
        return "admin/user/detail";
    }

    // get create page
    @GetMapping("/admin/users/create")
    public String getCreatePage(Model model) {
        User user = new User();
        List<Role> roles = this.roleService.getAllRole();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "admin/user/create";
    }

    // create
    @PostMapping("/admin/users/create")
    public String createPage(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation failed. Errors:" + bindingResult.getErrorCount());

            List<Role> roles = this.roleService.getAllRole();
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            return "admin/user/create";
        }
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRole(user.getRole().getRole_name()));

        this.userService.saveUser(user);
        return "redirect:/admin/users";
    }

    // get edit page
    @GetMapping("/admin/users/update/{id}")
    public String getEditPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserByID(id);
        // String email = user.getEmail();
        List<Role> roles = this.roleService.getAllRole();
        // model.addAttribute("email", email);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        model.addAttribute("roles", roles);
        return "admin/user/editUser";
    }

    // update
    @PostMapping("/admin/users/update")
    public String postUpdateUser(Model model, @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation failed. Errors:" + bindingResult.getErrorCount());
            // Duyệt qua danh sách lỗi
            // for (FieldError error : bindingResult.getFieldErrors()) {
            //     System.out.println("Field: " + error.getField() + " - Message: " + error.getDefaultMessage());
            // }
            List<Role> roles = this.roleService.getAllRole();
            model.addAttribute("user", user);
            model.addAttribute("id", user.getId());
            model.addAttribute("roles", roles);
            return "admin/user/editUser";
        }
        if (this.userService.getUserByID(user.getId()) != null) {
            
            User OUser = this.userService.getUserByID(user.getId());

            if (user != null) {
                OUser.setAddress(user.getAddress());
                OUser.setFullName(user.getFullName());
                OUser.setPhone(user.getPhone());
                OUser.setRole(user.getRole());
                this.userService.saveUser(OUser);
            }
        }
        return "redirect:/admin/users";
    }

    // update
    @GetMapping("/admin/users/delete/{id}")
    public String postUpdateUser(@PathVariable long id) {

        if (this.userService.getUserByID(id) != null) {
            this.userService.deleteUser(id);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin")
    public String adminDasboart() {
        return "admin";
    }

}
