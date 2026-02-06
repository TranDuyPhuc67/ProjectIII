package Fashionstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Fashionstore.entities.Product;

public interface ProductRepository extends JpaRepository<Product,String> {
	public List<Product> findTop5ByOrderByProductIdAsc();
	public List<Product> findByProductNameContaining(String name);
	List<Product> findByStatusTrue();
	List<Product> findTop5ByStatusTrueOrderByProductIdAsc();

}