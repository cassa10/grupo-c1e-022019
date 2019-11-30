package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.PendingOrder;
import com.desapp.grupoc1e022019.persistence.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public List<Order> getHistoricProviderOrdersTaken(Provider providerRecovered) {
        return orderRepository.findAllByProvider(providerRecovered);
    }

    public List<Order> getHistoricClientOrders(Client clientRecovered) {
        return orderRepository.findAllByClient(clientRecovered);
    }

    public Optional<Order> findOrderById(long idOrder) {
        return orderRepository.findById(idOrder);
    }

    public List<Order> findAllByPendingStateAndDeliverDateBetweenAndSortedByIdMenu(LocalDateTime minDateTime, LocalDateTime maxDateTime) {
        return orderRepository.
                findAllByStateAndDeliverDateAfterMinDateTimeAndBeforeMaxDateTimeAndSortedBy(
                        new PendingOrder().toString(), minDateTime, maxDateTime, new Sort(Sort.Direction.DESC,"menu.id"));
    }

    public Long countTotalAmountMenusByPendingStateAndIdMenuAndDeliverDateBetween(long idMenu, LocalDateTime minDateTime, LocalDateTime maxDateTime) {
        return orderRepository.countMenuTotalAmountsByStateAndIdMenuAndDeliverDateAfterMinDateTimeAndBeforeMaxDateTime(
                new PendingOrder().toString(), idMenu, minDateTime, maxDateTime);
    }
}
