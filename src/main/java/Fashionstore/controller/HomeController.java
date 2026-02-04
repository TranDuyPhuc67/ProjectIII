package Fashionstore.controller;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Fashionstore.entities.Account;
import Fashionstore.entities.AccountDetails;
import Fashionstore.entities.ERole;
import Fashionstore.repositories.AccountRepository;
import Fashionstore.services.ProductService;

@Controller
public class HomeController {
	@Autowired
	ProductService productService;
	@Autowired
	AccountRepository accountRepository; 
	@Autowired PasswordEncoder passwordEncoder;
	@GetMapping({"/","/trang-chu"})
	public String home(Model model) {
		model.addAttribute("top5", productService.getTop5());
		return "home/index";
	}
	@GetMapping("/about-us")
	public String about(Model model) {
		return "home/about";
	}
	@GetMapping("/login")
	public String login(Model model) {
		return "home/login";
	}
	@GetMapping("/register")
	public String register(Model model) {
		return "home/register";
	}
	@GetMapping("/forgot-password") 
	public String showForgotPasswordForm(Model model) { 
		return "home/forgot_password"; 
	}
	@GetMapping(value = "/success")
    public String success(){
        try {
            AccountDetails account = (AccountDetails) SecurityContextHolder.getContext().getAuthentication()
                   .getPrincipal();
           if (account.getAuthorities().toString().contains(ERole.ROLE_ADMIN.name()))
               return "redirect:/admin";
       } catch (Exception e) {
           e.printStackTrace();
       }
       return "redirect:/";
    }
	@GetMapping("/contact-us")
	public String contact(Model model) {
		return "home/contact";
	}
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("products", productService.getAll());
		return "home/shop";
	}
	@GetMapping("/blog")
	public String blog(Model model) {
		return "home/blog";
	}
	@GetMapping("/product")
	public String product(Model model) {
		return "home/product";
	}
	
	@PostMapping("/register")
	public String processRegister(Account account,
	                              @RequestParam("confirmPassword") String confirmPassword,
	                              Model model) {
	    if (!account.getPassword().equals(confirmPassword)) {
	        model.addAttribute("error", "Passwords do not match!");
	        return "home/register";
	    }

	    if (accountRepository.existsByUsername(account.getUsername())) {
	        model.addAttribute("error", "Username already exists!");
	        return "home/register";
	    }
	    if (accountRepository.existsByEmail(account.getEmail())) {
	        model.addAttribute("error", "Email already exists!");
	        return "home/register";
	    }

	    account.setPassword(passwordEncoder.encode(account.getPassword()));
	    account.setEnabled(true);

	    accountRepository.save(account);

	    model.addAttribute("success", "Registration successful! Please login.");
	    return "home/login";
	}
	@PostMapping("/forgot-password")
	public String processForgotPassword(@RequestParam("email") String email, Model model) {
	    Optional<Account> accountOpt = accountRepository.findByEmail(email);
	    if (accountOpt.isEmpty()) {
	        model.addAttribute("error", "Email not found!");
	        return "home/forgot_password";
	    }

	    model.addAttribute("success", "Password reset instructions have been sent to your email.");
	    return "home/forgot_password";
	}

}