package com.myphoneshop.myphoneshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myphoneshop.myphoneshop.domain.User;
import com.myphoneshop.myphoneshop.service.UploadService;
import com.myphoneshop.myphoneshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;

    public UserController(UserService userService, UploadService uploadService) {
        this.userService = userService;
        this.uploadService = uploadService;

    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsres = this.userService.getAllUserByEmail("buithikimngan2705@gmai.com");
        System.out.println(arrUsres);
        model.addAttribute("loc", "test");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUser();
        model.addAttribute("users", users);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("id", id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") User phuocloc,
            @RequestParam("phuoclocFile") MultipartFile file) {

        this.uploadService.handleUploadFile(file, "avatar");
        // this.userService.handleSubmit(phuocloc);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        model.addAttribute("id", id);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    // cái @ModelAttribute là lấy giá trị từ cái view.
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User phuocloc) {
        User currentUser = this.userService.getUserById(phuocloc.getId());
        if (currentUser != null) {
            currentUser.setAddress(phuocloc.getAddress());
            currentUser.setFullName(phuocloc.getFullName());
            currentUser.setPhone(phuocloc.getPhone());
            this.userService.handleSubmit(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user = new User();
        // user.setId(id);
        // model.addAttribute("newUser", user);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUserPage(Model model, @ModelAttribute("newUser") User phuocloc) {
        this.userService.deleteAUser(phuocloc.getId());
        return "redirect:/admin/user";
    }

}
