package Fashionstore.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Fashionstore.entities.Account;
import Fashionstore.entities.CartItem;
import Fashionstore.entities.Order;
import Fashionstore.entities.OrderDetail;
import Fashionstore.entities.Product;
import Fashionstore.models.Item;
import Fashionstore.repositories.AccountRepository;
import Fashionstore.repositories.CartItemRepository;
import Fashionstore.repositories.OrderDetailRepository;
import Fashionstore.repositories.OrderRepository;
import Fashionstore.repositories.ProductRepository;

@Service
public class CartService {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderDetailRepository detailRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ProductRepository productRepository;
	@Autowired 
	private OrderDetailRepository orderDetailRepository;
	@Autowired 
	private CartItemRepository cartItemRepository;
	public List<Item> addCart(Product product, List<Item> items) {
		var find = false;
		for (Item item : items) {
			if (item.getProductId().equals(product.getProductId())) {
				item.setQuantity(item.getQuantity() + 1);
				item.setTotal(item.getPrice() * item.getQuantity());
				find = true;
				break;
			}
		}
		if (!find) {
			items.add(new Item(product));
		}
		return items;
	}
	public void removeCart(String username, String productId) {
	    Account acc = accountRepository.findByUsername(username).orElse(null);
	    if (acc == null) return;

	    Product product = productRepository.findById(productId).orElse(null);
	    if (product == null) return;

	    Optional<CartItem> existing = cartItemRepository.findByAccountAndProduct(acc, product);
	    if (existing.isPresent()) {
	        CartItem item = existing.get();
	        if (item.getQuantity() > 1) {
	            item.setQuantity(item.getQuantity() - 1);
	            cartItemRepository.save(item); 
	        } else {
	            cartItemRepository.delete(item);
	        }
	    }
	}

	public void insertOrder(Order order, List<CartItem> cartItems) {
	    orderRepository.save(order);

	    for (CartItem ci : cartItems) {
	        OrderDetail detail = new OrderDetail();
	        detail.setOrder(order);
	        detail.setProduct(ci.getProduct());
	        detail.setQuantity(ci.getQuantity());
	        detail.setPrice(ci.getProduct().getPrice());
	        orderDetailRepository.save(detail);
	    }
	}

}