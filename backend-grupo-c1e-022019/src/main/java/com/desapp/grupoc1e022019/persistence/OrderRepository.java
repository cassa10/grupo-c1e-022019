package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT COUNT(o) from Order o WHERE o.orderState.class = ?3 AND o.menu = ?1 AND DATE(o.deliverType.deliverDate) = DATE(?2)")
    Long countByMenuAndDeliverDateAndStateOrder(Menu menuRecovered, LocalDateTime deliverDate, String orderState);
}
