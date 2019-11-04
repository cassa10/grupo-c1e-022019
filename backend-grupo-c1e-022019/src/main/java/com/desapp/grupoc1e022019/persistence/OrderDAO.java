package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
