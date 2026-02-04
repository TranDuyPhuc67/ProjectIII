package Fashionstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Fashionstore.entities.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

}