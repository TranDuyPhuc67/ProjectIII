package Fashionstore.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import Fashionstore.entities.Account;
import Fashionstore.entities.CartItem;
import Fashionstore.entities.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByAccount(Account account);
    Optional<CartItem> findByAccountAndProduct(Account account, Product product);
    List<CartItem> findByAccountAndProduct_StatusTrue(Account account);

}
