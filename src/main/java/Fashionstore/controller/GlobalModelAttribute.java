package Fashionstore.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import Fashionstore.entities.Account;
import Fashionstore.entities.CartItem;
import Fashionstore.repositories.AccountRepository;
import Fashionstore.repositories.CartItemRepository;

@ControllerAdvice
public class GlobalModelAttribute {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    @ModelAttribute
    public void addMiniCartData(org.springframework.ui.Model model, Principal principal) {

        // Chưa login → giỏ rỗng
        if (principal == null) {
            model.addAttribute("cartitems", List.of());
            model.addAttribute("totalCart", 0);
            return;
        }

        String username = principal.getName();
        Account acc = accountRepo.findByUsername(username).orElse(null);

        if (acc == null) {
            model.addAttribute("cartitems", List.of());
            model.addAttribute("totalCart", 0);
            return;
        }

        List<CartItem> cartItems = cartItemRepo.findByAccount(acc);

        // ❗ DỌN CART ITEM LỖI (product null, productId null)
        cartItems.removeIf(item ->
            item.getProduct() == null ||
            item.getProduct().getProductId() == null
        );

        double totalCart = cartItems.stream()
                .mapToDouble(CartItem::getTotal)
                .sum();

        model.addAttribute("cartitems", cartItems);
        model.addAttribute("totalCart", totalCart);
    }
}
