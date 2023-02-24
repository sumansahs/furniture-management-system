package com.example.furniture.Controller;



import com.example.furniture.Entity.Order;
import com.example.furniture.Entity.Product;
import com.example.furniture.Entity.User;
import com.example.furniture.Pojo.OrderPojo;
import com.example.furniture.Pojo.ProductPojo;
import com.example.furniture.Pojo.UserPojo;
import com.example.furniture.Services.OrderServices;
import com.example.furniture.Services.ProductServices;
import com.example.furniture.Services.UserServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserServices userService;
    private final ProductServices productServices;
    private final OrderServices orderServices;
    @GetMapping("/homepage")
    public String getSetting(Model model ,Principal principal,
                             Authentication authentication) {
        if (authentication!=null){
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority().equals("Admin")) {
                    return "redirect:/admin/viewallproduct";
                }
            }
        }
        List<Product> productList=productServices.findAll();
        model.addAttribute("add", productList);

        List<Product> products = productServices.getThreeRandomData();

        model.addAttribute("productfetch", products);
        model.addAttribute("userdata",userService.findByEmail(principal.getName()));

        return "index";
    }

    @GetMapping("/signup") //first maa chaine kura
    public String getSignupPage(Model model) {
        model.addAttribute("create", new UserPojo());
        return "signin";
    }
    @PostMapping("/saveuser")
    public String saveUser(@Valid UserPojo userPojo) {
        userService.save(userPojo);
        return "redirect:/signup";
    }


    @PostMapping("/saveorder")
    public String getHire(@Valid OrderPojo orderPojo){
        orderServices.save(orderPojo);
        return "redirect:/user/homepage";
    }


    @GetMapping("/productinfo/{id}")
    public String GetmoreInfo(@PathVariable("id") Integer id , Model model, Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/user/signup";
        }
        Product product= productServices.findById(id);
        model.addAttribute("order", new OrderPojo());
        model.addAttribute("productinfo",new ProductPojo(product));
        model.addAttribute("productdata",product);
        model.addAttribute("info",userService.findByEmail(principal.getName()));
        return "indiProduct";
    }

    @GetMapping("/viewAllMyOrders/{id}")
    public String getOrderinList(@PathVariable("id") Integer id, Model model, Principal principal) {
        List<Order> order=orderServices.findOrderById(id);
        model.addAttribute("orderList", order);
        model.addAttribute("userdata",userService.findByEmail(principal.getName()));

        User user = userService.findBYId(id);
        model.addAttribute("signup", new UserPojo(user));
        model.addAttribute("signups", user);
        return "profile";
    }


    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id) {
        orderServices.deleteById(id);
        return "redirect:/user/homepage";
    }


    @PostMapping("/updateprofile")
    public String updateRegister(@Valid UserPojo userPojo){
        userService.save(userPojo);
        return "redirect:/user/homepage";}






}

