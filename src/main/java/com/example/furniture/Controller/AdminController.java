package com.example.furniture.Controller;

import com.example.furniture.Entity.Order;
import com.example.furniture.Entity.Product;
import com.example.furniture.Pojo.ProductPojo;
import com.example.furniture.Services.OrderServices;
import com.example.furniture.Services.ProductServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private  final ProductServices productServices;
    private  final OrderServices orderServices;
    @GetMapping("/addproduct")
    public String getAddProductForm(Model model) {
        model.addAttribute("add",new ProductPojo());
        return "Admin/AddProduct";
    }
    @PostMapping("/saveproduct")
    public String createUser(@Valid ProductPojo productPojo,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes
    ) throws IOException {

        Map<String, String> requestError = validateRequest(bindingResult);
        if (requestError != null) {
            redirectAttributes.addFlashAttribute("requestError", requestError);
            return "redirect:/admin/addproduct";
        }
        productServices.save(productPojo);
        redirectAttributes.addFlashAttribute("successMsg", "User saved successfully");
        return "redirect:/admin/viewallproduct";


    }
    @GetMapping("/viewallproduct")
    public String getProductinList(Model model) {
        List<Product> productList=productServices.findAll();
        model.addAttribute("alllist", productList);
//        return "Admin/AddProduct";
        return "Admin/allProductList";
    }

    @GetMapping("/editproduct/{id}")
    public String editMembers(@PathVariable("id") Integer id, Model model) {
        Product product = productServices.findById(id);
        model.addAttribute("add", new ProductPojo(product));
        return "Admin/AddProduct";
    }

    @GetMapping("/deleteproduct/{id}")
    public String deleteMembers(@PathVariable("id") Integer id) {
        productServices.deleteById(id);
        return "redirect:/admin/viewallproduct";
    }


    @GetMapping("/viewallorder")
    public String getOrderList(Model model) {
        List<Order> orders=orderServices.findAll();
        model.addAttribute("alllist", orders);
        return "Admin/ViewOrders";
    }



    public Map<String, String> validateRequest(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return null;
        }
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;

    }

}
