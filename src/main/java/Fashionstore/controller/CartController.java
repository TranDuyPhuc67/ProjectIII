package Fashionstore.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Fashionstore.entities.Account;
import Fashionstore.entities.CartItem;
import Fashionstore.entities.Order;
import Fashionstore.entities.Product;
import Fashionstore.repositories.AccountRepository;
import Fashionstore.repositories.CartItemRepository;
import Fashionstore.repositories.ProductRepository;
import Fashionstore.services.CartService;
import Fashionstore.services.ProductService;
@Controller
public class CartController {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
	CartService cartService;
    
    @Autowired
	ProductService productService;
    
    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/cart") 
    public String viewCart(Principal principal, Model model) { 
    	String username = principal.getName(); 
    	Account acc = accountRepo.findByUsername(username).orElseThrow(); 
    	List<CartItem> cartItems = cartItemRepo.findByAccount(acc); 
    	cartItems.removeIf(item ->
        item.getProduct() == null ||
        item.getProduct().getProductId() == null
    );
    	double totalCart = cartItems.stream().mapToDouble(CartItem::getTotal).sum(); 
    	model.addAttribute("cartitems", cartItems); 
    	model.addAttribute("totalCart", totalCart); 
    	return "home/shopingcart"; 
    	}

    @PostMapping("/addcart") 
    public String addCart(@RequestParam("productId") String productId, 
    					  @RequestParam(defaultValue = "1") int quantity, 
    					  Principal principal) { String username = principal.getName(); 
    					  Account acc = accountRepo.findByUsername(username).orElseThrow(); 
    					  Product product = productRepo.findById(productId).orElseThrow(); 
    					  Optional<CartItem> existing = cartItemRepo.findByAccountAndProduct(acc, product); 
    					  if (existing.isPresent()) { 
    						  CartItem item = existing.get(); 
    						  item.setQuantity(item.getQuantity() + quantity); 
    						  cartItemRepo.save(item); 
    						  } else {
    							  CartItem item = new CartItem(); 
    							  item.setAccount(acc); 
    							  item.setProduct(product); 
    							  item.setQuantity(quantity); 
    							  cartItemRepo.save(item); 
    							  } return "redirect:/cart"; 
    							  }

    @GetMapping("/removeCart/{id}")
    public String removeCart(@PathVariable("id") String productId,
                             Principal principal) {
        String username = principal.getName();
        cartService.removeCart(username, productId);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(Order order, Model model, Principal principal) {
        String username = principal.getName();
        Account acc = accountRepo.findByUsername(username).orElseThrow();

        List<CartItem> cartItems = cartItemRepo.findByAccount(acc);
        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("msg", "Giỏ hàng trống!");
            return "home/shopingcart";
        }

        double total = cartItems.stream().mapToDouble(CartItem::getTotal).sum();
        order.setTotal(total);

        order.setOrderDate(LocalDateTime.now());
        order.setReceivedate(LocalDateTime.now().plusDays(3));
        order.setStatus(0);
        order.setAccount(acc);
        
        cartService.insertOrder(order, cartItems);

        model.addAttribute("order", order);
        model.addAttribute("items", cartItems);

        return "home/invoice";
    }
}
