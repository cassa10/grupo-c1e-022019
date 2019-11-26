package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.orderComponents.deliverType.DeliverType;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.PendingOrder;
import com.desapp.grupoc1e022019.services.ClientService;
import com.desapp.grupoc1e022019.services.GoogleAuthService;
import com.desapp.grupoc1e022019.services.MenuService;
import com.desapp.grupoc1e022019.services.OrderService;
import com.desapp.grupoc1e022019.services.builder.OrderBuilder;
import com.desapp.grupoc1e022019.services.dtos.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "orderController")
public class OrderController {

    @Autowired
    private OrderService orderService = new OrderService();

    @Autowired
    private MenuService menuService = new MenuService();

    @Autowired
    private ClientService clientService = new ClientService();

    @Autowired
    private GoogleAuthService googleAuthService = new GoogleAuthService();

    @RequestMapping(method = RequestMethod.POST, value = "/order")
    public ResponseEntity getClient(@RequestBody OrderDTO orderDTO) {

        if(! googleAuthService.clientHasAccess(orderDTO.getGoogleId(),orderDTO.getTokenAccess())) {
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        Optional<Client> recoverClient = clientService.getClient(orderDTO.getIdClient());
        Optional<Menu> recoverMenu = menuService.getMenu(orderDTO.getIdMenu());

        if(!recoverClient.isPresent() || !recoverMenu.isPresent()) {
            return new ResponseEntity<>("Invalid data request", HttpStatus.BAD_REQUEST);
        }

        if(recoverMenu.get().isCancelledState()){
            return new ResponseEntity<>("Sorry, this menu is cancelled",HttpStatus.BAD_REQUEST);
        }

        if(recoverClient.get().isClientHaveToRank()){
            return new ResponseEntity<>("Rank your previous orders, please",HttpStatus.BAD_REQUEST);
        }

        if(hasErrorDataOrderDTO(orderDTO)){
            return new ResponseEntity<>("Invalid data form",HttpStatus.BAD_REQUEST);
        }

        DeliverType deliverType = orderDTO.parseDeliveryType();

        Order newOrder = new OrderBuilder()
                                .withMenusAmount(orderDTO.getMenusAmount())
                                .withMenu(recoverMenu.get())
                                .withClient(recoverClient.get())
                                .withDeliverType(deliverType)
                                .withState(new PendingOrder())
                                .build();

        if(notEnoughCredit(recoverClient.get().getCredit(),recoverMenu.get(),orderDTO.getMenusAmount())) {
            return new ResponseEntity<>("Client does not has enough credits",HttpStatus.NOT_ACCEPTABLE);
        }

        newOrder = orderService.createOrder(newOrder);

        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

    private boolean notEnoughCredit(Credit credit, Menu menu, Integer menusAmount) {
        return ! credit.isGreaterOrEqual(new Credit(menu.priceWithAmount(menusAmount)));
    }

    private boolean hasErrorDataOrderDTO(OrderDTO orderDTO) {
        return orderDTO.getMenusAmount() <= 0 ||
                orderDTO.getDeliverType() == null ||
                orderDTO.getMenusAmount() == null ||
                ! orderDTO.hasValidDeliverType() ||
                orderDTO.getDeliverDate().isAfter(LocalDateTime.now().plusDays(2)) ||
                orderDTO.getDeliverDate().isBefore(LocalDateTime.now().plusHours(1));
    }

}
