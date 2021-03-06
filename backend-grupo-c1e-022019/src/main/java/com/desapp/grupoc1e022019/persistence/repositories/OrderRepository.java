package com.desapp.grupoc1e022019.persistence.repositories;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.Provider;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT COUNT(o) from Order o WHERE o.orderState.class = ?3 AND o.menu = ?1 AND DATE(o.deliverType.deliverDate) = DATE(?2)")
    Long countByMenuAndDeliverDateAndStateOrder(Menu menuRecovered, LocalDateTime deliverDate, String orderState);

    @Query("SELECT o from Order o inner join o.menu m WHERE o.orderState.class != CancelledOrder AND m.provider = ?1")
    List<Order> findAllByProvider(Provider providerRecovered);

    List<Order> findAllByClient(Client clientRecovered);

    @Query("SELECT o from Order o WHERE o.orderState.class = ?1 AND o.deliverType.deliverDate >= ?2  AND o.deliverType.deliverDate <= ?3")
    List<Order> findAllByStateAndDeliverDateAfterMinDateTimeAndBeforeMaxDateTimeAndSortedBy(String orderState, LocalDateTime dateTime1, LocalDateTime dateTime2, Sort orders);

    @Query("SELECT SUM(o.menusAmount) from Order o WHERE o.orderState.class = ?1 AND o.menu.id = ?2 AND o.deliverType.deliverDate >= ?3  AND o.deliverType.deliverDate <= ?4")
    Long countMenuTotalAmountsByStateAndIdMenuAndDeliverDateAfterMinDateTimeAndBeforeMaxDateTime(String toString, long idMenu, LocalDateTime minDateTime, LocalDateTime maxDateTime);
}
