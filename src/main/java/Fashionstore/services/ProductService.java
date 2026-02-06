package Fashionstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Fashionstore.entities.Product;
import Fashionstore.repositories.ProductRepository;
@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	public boolean existsById(String id) { 
		return productRepository.existsById(id);
	}
	public List<Product> getTop5(){
	    return productRepository.findTop5ByStatusTrueOrderByProductIdAsc();
	}

	public List<Product> getAll(){
		return productRepository.findAll();
	}
	public Product getById(String productId) {
		return productRepository.findById(productId).get();
	}
	public List<Product> findAll() { 
		return productRepository.findAll(); 
	} 
	public Product findById(String id) { 
		return productRepository.findById(id).orElse(null); 
	} 
	public Product save(Product p) { 
		return productRepository.save(p); 
	} 
	public void delete(String id) { 
		productRepository.deleteById(id); 
	}
	public void deactivate(String id) { 
		Product p = productRepository.findById(id).orElse(null); 
		if (p != null) { p.setStatus(false); 
		productRepository.save(p); 
		} 
	}
	public List<Product> findActiveProducts() {
	    return productRepository.findByStatusTrue();
	}

}