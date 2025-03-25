package ks.training.sportsShop.controller.admin;

import ks.training.sportsShop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {
    private final UserService userService;

    public CategoryController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/category")
    public String getDashboard(Model model) {
        return "admin/category/show";
    }

}
