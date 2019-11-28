package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.orderComponents.deliverType.DeliverType;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.PendingOrder;
import com.desapp.grupoc1e022019.services.*;
import com.desapp.grupoc1e022019.services.builder.OrderBuilder;
import com.desapp.grupoc1e022019.services.dtos.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    @Autowired
    private EmailSenderService emailSenderService = new EmailSenderService();

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

        if(! orderDTO.formIsValid()){
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

        if(orderService.providerHasReachOrdersLimit(recoverMenu.get(), newOrder.getDeliverDate())){
            return new ResponseEntity<>("Menu provider reach own limit of orders, try another deliver date",HttpStatus.NOT_ACCEPTABLE);
        }

        if(notEnoughCredit(recoverClient.get().getCredit(),recoverMenu.get(),orderDTO.getMenusAmount())) {
            return new ResponseEntity<>("Client does not has enough credits",HttpStatus.NOT_ACCEPTABLE);
        }

        //TRANSACTIONAL
        newOrder = orderService.createOrder(newOrder);

        //ASYNC METHOD
        emailSenderService.sendOrderPendingEmail(newOrder);

        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/order/menu/size")
    public ResponseEntity getSizeMenuOrderLimitByDate(@RequestParam HashMap<String,String> body){
        String googleId = body.get("googleId");
        String tokenAccess = body.get("tokenAccess");

        if(! googleAuthService.userHasAccess(googleId,tokenAccess)){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        LocalDateTime date = LocalDateTime.parse(body.get("deliverDate"));

        long idMenu = Long.parseLong(body.get("idMenu"));

        Optional<Menu> recoverMenu = menuService.getMenu(idMenu);

        if(! recoverMenu.isPresent()){
            return new ResponseEntity<>("Invalid data request", HttpStatus.BAD_REQUEST);
        }

        Long value = orderService.sizeOrdersLimit(recoverMenu.get(),date);
        return new ResponseEntity<>(value,HttpStatus.OK);
    }

    private boolean notEnoughCredit(Credit credit, Menu menu, Integer menusAmount) {
        return ! credit.isGreaterOrEqual(new Credit(menu.priceWithAmount(menusAmount)));
    }

}
