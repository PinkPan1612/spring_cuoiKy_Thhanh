package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.UploadFileService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController {

    private UploadFileService uploadService;
    private ProductService productService;

    public ProductController(UploadFileService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }

    // mở trang create
    @GetMapping("/admin/products/create")
    public String getProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "admin/product/create";
    }

    // post create
    @PostMapping("/admin/products/create")
    public String postCreateProduct(@ModelAttribute("product") @Valid Product newProduct,
            BindingResult newProductBindingResult,
            @RequestParam("imageProduct") MultipartFile file) {
        // Kiểm tra nếu có lỗi validation
        if (newProductBindingResult.hasErrors()) {
            System.out.println("Validation failed. Errors:" + newProductBindingResult.getErrorCount());
            // Duyệt qua danh sách lỗi
            for (FieldError error : newProductBindingResult.getFieldErrors()) {
                System.out.println("Field: " + error.getField() + " - Message: " + error.getDefaultMessage());
            }
            return "admin/product/create";
        }
        // lấy đường dẫn hình
        String product_image = this.uploadService.handleSaveUploadFile(file, "img");
        newProduct.setImage(product_image);
        this.productService.saveProduct(newProduct);

        return "redirect:/admin/products";
    }

    // mở form list
    @GetMapping("/admin/products")
    public String getformList(Model model) {
        List<Product> products = this.productService.getAllPr();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    // mở form detail
    @GetMapping("/admin/products/detail/{id}")
    public String getformDetail(Model model, @PathVariable long id) {
        Product product = this.productService.getProductByID(id);
        model.addAttribute("id", id);
        model.addAttribute("productDetail", product);
        return "admin/product/detail";
    }

    // mở form detail
    @GetMapping("/admin/products/delete/{id}")
    public String getdeleteProduct(Model model, @PathVariable long id) {
        this.productService.deleteProductserrvice(id);
        return "redirect:/admin/products";
    }

    // mở form edit
    @GetMapping("/admin/products/update/{id}")
    public String getEditProductPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductByID(id);
        model.addAttribute("id", id);
        model.addAttribute("product", product);
        return "admin/product/edit";
    }

    // chỉnh sửa
    @PostMapping("/admin/products/update")
    public String postUpdateProduct(@ModelAttribute("product") @Valid Product newProduct,
            BindingResult newProductBindingResult,
            @RequestParam("imageProduct") MultipartFile file) {
        // Kiểm tra nếu có lỗi validation
        if (newProductBindingResult.hasErrors()) {
            System.out.println("Validation failed. Errors:" + newProductBindingResult.getErrorCount());
            // Duyệt qua danh sách lỗi
            for (FieldError error : newProductBindingResult.getFieldErrors()) {
                System.out.println("Field: " + error.getField() + " - Message: " + error.getDefaultMessage());
            }

            return "admin/product/edit";
        }
        // lấy đường dẫn hình
        String product_image = this.uploadService.handleSaveUploadFile(file, "img");
        newProduct.setImage(product_image);
        this.productService.saveProduct(newProduct);

        return "redirect:/admin/products";
    }

}
