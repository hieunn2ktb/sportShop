package ks.training.sportsShop.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import ks.training.sportsShop.entity.Order;
import ks.training.sportsShop.entity.OrderDetail;
import ks.training.sportsShop.entity.Product;
import ks.training.sportsShop.service.OrderService;
import ks.training.sportsShop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;

    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/admin/order")
    public String getDashboard(Model model,
                               @RequestParam("page") Optional<String> pageOptional,
                               @RequestParam(required = false) String customerName,
                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            } else {
            }
        } catch (Exception e) {
        }

        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Order> ordersPage = this.orderService.fetchAllOrders(customerName, startDate,endDate, pageable);
        List<Order> orders = ordersPage.getContent();
        int totalPages = ordersPage.getTotalPages();
        if (totalPages == 0) {
            totalPages = 1;
        }
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetailPage(Model model, @PathVariable long id) {
        Order order = this.orderService.fetchOrderById(id).get();
        model.addAttribute("order", order);
        model.addAttribute("id", id);
        model.addAttribute("orderDetails", order.getOrderDetails());
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getDeleteOrderPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newOrder", new Order());
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String postDeleteOrder(@ModelAttribute("newOrder") Order order) {
        this.orderService.deleteOrderById(order.getId());
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrderPage(Model model, @PathVariable long id) {
        Optional<Order> currentOrder = this.orderService.fetchOrderById(id);
        model.addAttribute("newOrder", currentOrder.get());
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String handleUpdateOrder(@ModelAttribute("newOrder") Order order,@RequestParam String status,@RequestParam(required = false) String cancelReason) {
        this.orderService.updateOrder(order,status,cancelReason);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/create")
    public String getCrateOrder(Model model){
        List<Product> products = productService.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String productsJson = "";
        try {
            productsJson = objectMapper.writeValueAsString(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Order order = new Order();

        model.addAttribute("productsJson", productsJson);
        model.addAttribute("newOrder", order);
        model.addAttribute("products", products);
        return "admin/order/create";
    }
    @PostMapping("/admin/order/create")
    public String handleCreateOrder(
        @ModelAttribute("newOrder") @Valid Order order,
        BindingResult newProductBindingResult){
        if (newProductBindingResult.hasErrors()) {
            return "admin/order/create";
        }
        this.orderService.createOrder(order);
        return "redirect:/admin/order";
    }
}
