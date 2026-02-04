package Fashionstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Fashionstore.entities.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}