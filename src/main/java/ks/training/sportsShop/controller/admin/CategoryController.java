package ks.training.sportsShop.controller.admin;

import jakarta.validation.Valid;
import ks.training.sportsShop.entity.Category;
import ks.training.sportsShop.entity.Product;
import ks.training.sportsShop.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/category")
    public String getDashboard(Model model,
                               @RequestParam("page") Optional<String> pageOptional,
                               @RequestParam(required = false) String categoryName
                               ) {

        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            } else {
            }
        } catch (Exception e) {
        }

        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Category> categories = this.categoryService.getAllCategories(categoryName, pageable);
        List<Category> categoryList = categories.getContent();
        int totalPages = categories.getTotalPages();
        if (totalPages == 0) {
            totalPages = 1;
        }
        model.addAttribute("category", categoryList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "admin/category/show";
    }
    @GetMapping("/admin/category/create")
    public String getCreateCategory(Model model){
        model.addAttribute("newCategory",new Category());
        return "admin/category/create";
    }
    @PostMapping("/admin/category/create")
    public String handelCreateCategory(@ModelAttribute("newCategory") @Valid Category category,
                                       BindingResult newProductBindingResult){
        if (newProductBindingResult.hasErrors()) {
            return "admin/category/create";
        }
        this.categoryService.createCategory(category);

        return "redirect:/admin/category";
    }
    @GetMapping("/admin/category/update/{id}")
    public String getUpdateCategory(Model model, @PathVariable long id){
        Category currentCategory = this.categoryService.fetchCategoryById(id);
        model.addAttribute("newCategory", currentCategory);
        return "admin/category/update";
    }
    @PostMapping("/admin/category/update")
    public String handleUpdateCategory(@ModelAttribute("newCategory") @Valid Category cat,
                                      BindingResult newProductBindingResult) {
        if (newProductBindingResult.hasErrors()) {
            return "admin/category/update";
        }
        Category category = categoryService.fetchCategoryById(cat.getId());
        if (category != null){
            category.setName(cat.getName());
            this.categoryService.createCategory(category);
        }
        return "redirect:/admin/category";
    }


    }
