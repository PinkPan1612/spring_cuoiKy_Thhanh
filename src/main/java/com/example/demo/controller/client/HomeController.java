package com.example.demo.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@Controller
public class HomeController {
    private ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        return "client/home";
    }

    @GetMapping("/detail/{id}")
    public String getDetailPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductByID(id);
        model.addAttribute("product", product);
        return "client/detail";
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "/client/about";
    }

    @GetMapping("/cart")
    public String getCartPage() {
        return "client/cart";
    }

    @GetMapping("/contact")
    public String getContactPage() {
        return "client/contact";
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products = this.productService.getAllPr();
        model.addAttribute("products", products);
        return "client/products";
    }
}
