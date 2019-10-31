package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
