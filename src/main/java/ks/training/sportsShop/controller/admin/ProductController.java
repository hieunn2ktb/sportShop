package ks.training.sportsShop.controller.admin;

import jakarta.validation.Valid;
import ks.training.sportsShop.entity.Category;
import ks.training.sportsShop.entity.Product;
import ks.training.sportsShop.service.CategoryService;
import ks.training.sportsShop.service.ProductService;
import ks.training.sportsShop.service.UploadService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;
    private final CategoryService categoryService;
    public ProductController(ProductService productService, UploadService uploadService, CategoryService categoryService) {
        this.productService = productService;
        this.uploadService = uploadService;
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/product")
    public String getProduct(
            Model model,
            @RequestParam("page") Optional<String> pageOptional,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            } else {
            }
        } catch (Exception e) {
        }

        List<Category> categories = categoryService.getAllCategories();
        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Product> prs = this.productService.fetchProducts(name,category,pageable);
        List<Product> listProducts = prs.getContent();
        int totalPages = prs.getTotalPages();
        if (totalPages == 0){
            totalPages = 1;
        }
        model.addAttribute("products", listProducts);

        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "admin/product/show";
    }
    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }
    @PostMapping("/admin/product/create")
    public String handleCreateProduct(
            @ModelAttribute("newProduct") @Valid Product pr,
            BindingResult newProductBindingResult,
            @RequestParam("productFile") MultipartFile file) {
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String image = this.uploadService.handleSaveUploadFile(file, "product");
        pr.setImage(image);
        pr.setCategory(this.productService.getCategoryById(pr.getCategory().getId()));
        this.productService.createProduct(pr);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Optional<Product> currentProduct = this.productService.fetchProductById(id);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("newProduct", currentProduct.get());
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateProduct(@ModelAttribute("newProduct") @Valid Product pr,
                                      BindingResult newProductBindingResult,
                                      @RequestParam("productFile") MultipartFile file) {

        // validate
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }

        Product currentProduct = this.productService.fetchProductById(pr.getId()).get();
        if (currentProduct != null) {
            // update new image
            if (!file.isEmpty()) {
                String img = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(img);
            }

            currentProduct.setName(pr.getName());
            currentProduct.setPrice(pr.getPrice());
            currentProduct.setQuantity(pr.getQuantity());
            currentProduct.setDetailDesc(pr.getDetailDesc());
            currentProduct.setShortDesc(pr.getShortDesc());
            currentProduct.setBrand(pr.getBrand());
            currentProduct.setSize(pr.getSize());
            currentProduct.setSportType(pr.getSportType());
            System.out.println(pr.getCategory().getName());
            Category category = this.productService.getCategoryById(pr.getCategory().getId());
            System.out.println(category);
            currentProduct.setCategory(category);
            this.productService.createProduct(currentProduct);
        }

        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("newProduct") Product pr , RedirectAttributes redirectAttributes) {

        long productId = pr.getId();
        boolean existsInOrder = productService.existsByProductId(productId);

        if (existsInOrder) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa sản phẩm vì đã có trong đơn hàng.");
            return "redirect:/admin/product";
        }

        this.productService.deleteProduct(pr.getId());
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product pr = this.productService.fetchProductById(id).get();
        model.addAttribute("product", pr);
        model.addAttribute("id", id);
        return "admin/product/detail";
    }

}
