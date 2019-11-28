package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.PendingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderDAO {

    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order){
        return orderRepository.save(order);
    }

    public void delete(Long id){
        orderRepository.deleteById(id);
    }

    public Order getOrder(long id){
        return orderRepository.getOne(id);
    }

    public Long sizeOfOrdersPerDayOfMenu(Menu menuRecovered, LocalDateTime deliverDate) {
        return orderRepository.countByMenuAndDeliverDateAndStateOrder(menuRecovered,deliverDate,new PendingOrder().toString());
    }
}
