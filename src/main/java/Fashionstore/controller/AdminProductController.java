package Fashionstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Fashionstore.entities.Product;
import Fashionstore.services.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/product";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Product product) {
        if (productService.existsById(product.getProductId())) {
            return "redirect:/admin?error=id_exists";
        }
        productService.save(product);
        return "redirect:/admin";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/form"; 
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin/form"; 
    }


    @PostMapping("/edit")
    public String edit(@ModelAttribute Product product) {
        Product existing = productService.findById(product.getProductId());
        if (existing != null) {
            existing.setProductName(product.getProductName());
            existing.setPrice(product.getPrice());
            existing.setDescription(product.getDescription());
            productService.save(existing);
        }
        return "redirect:/admin";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        productService.deactivate(id);
        return "redirect:/admin";
    }

}
