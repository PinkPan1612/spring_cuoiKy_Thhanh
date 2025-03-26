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
    public String getMethodName(Model model) {
        return "client/home";
    }

    @GetMapping("/about")
    public String getaboutPage() {
        return "/client/about";
    }

    @GetMapping("/cart")
    public String getcartPage() {
        return "client/cart";
    }

    @GetMapping("/detail/{id}")
    public String getdetailPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductByID(id);
        model.addAttribute("product", product);
        return "client/detailProduct";
    }

    @GetMapping("/menu")
    public String getMenu(Model model) {
        List<Product> products = this.productService.getAllPr();
        model.addAttribute("products", products);
        return "client/menu";
    }
}
