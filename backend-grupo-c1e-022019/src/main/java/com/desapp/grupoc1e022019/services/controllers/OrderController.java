package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.model.*;
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
    private ProviderService providerService = new ProviderService();

    @Autowired
    private GoogleAuthService googleAuthService = new GoogleAuthService();

    @Autowired
    private EmailSenderService emailSenderService = new EmailSenderService();

    @RequestMapping(method = RequestMethod.POST, value = "/order")
    public ResponseEntity createOrder(@RequestBody OrderDTO orderDTO) {

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

        //TRANSACTIONAL (+ send email)
        newOrder = orderService.createOrder(newOrder);

        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/order/menu/size")
    public ResponseEntity getSizeMenuOrderLimitByDate(@RequestParam HashMap<String,String> body){
        String googleId = body.get("googleId");
        String tokenAccess = body.get("tokenAccess");
        LocalDateTime date;
        long idMenu;

        if(! googleAuthService.userHasAccess(googleId,tokenAccess)){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        try{
            date = LocalDateTime.parse(body.get("deliverDate"));
            idMenu = Long.parseLong(body.get("idMenu"));
        }catch (Exception e){
            return new ResponseEntity<>("Invalid data request", HttpStatus.BAD_REQUEST);
        }

        Optional<Menu> recoverMenu = menuService.getMenu(idMenu);

        if(! recoverMenu.isPresent()){
            return new ResponseEntity<>("Invalid data request", HttpStatus.BAD_REQUEST);
        }

        Long value = orderService.sizeOrdersPerDay(recoverMenu.get(),date);
        return new ResponseEntity<>(value,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/order/provider/all")
    public ResponseEntity getProviderOrdersTaken(@RequestParam HashMap<String,String> body){
        String googleId = body.get("googleId");
        String tokenAccess = body.get("tokenAccess");
        long idProvider;

        if(! googleAuthService.providerHasAccess(googleId,tokenAccess)){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        try{
            idProvider = Long.parseLong(body.get("idProvider"));
        }catch (Exception e){
            return new ResponseEntity<>("Invalid data request", HttpStatus.BAD_REQUEST);
        }

        Optional<Provider> maybeProvider = providerService.findProviderById(idProvider);

        if(! maybeProvider.isPresent()){
            return new ResponseEntity<>("Invalid data request", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(orderService.getHistoricProviderOrdersTaken(maybeProvider.get()),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/order/client/all")
    public ResponseEntity getClientOrders(@RequestParam HashMap<String,String> body){
        String googleId = body.get("googleId");
        String tokenAccess = body.get("tokenAccess");
        long idClient;

        if(! googleAuthService.clientHasAccess(googleId,tokenAccess)){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        try{
            idClient = Long.parseLong(body.get("idClient"));
        }catch (Exception e){
            return new ResponseEntity<>("Invalid data request", HttpStatus.BAD_REQUEST);
        }

        Optional<Client> maybeClient = clientService.findClientById(idClient);

        if(! maybeClient.isPresent()){
            return new ResponseEntity<>("Invalid data request", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(orderService.getHistoricClientOrders(maybeClient.get()),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/order/cancel")
    public ResponseEntity cancelOrder(@RequestBody HashMap<String,String> body) {

        String googleId = body.get("googleId");
        String tokenAccess = body.get("tokenAccess");
        long idOrder;
        long idClient;

        if(! googleAuthService.clientHasAccess(googleId,tokenAccess)){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        try{
            idClient = Long.parseLong(body.get("idClient"));
            idOrder = Long.parseLong(body.get("idOrder"));
        }catch (Exception e){
            return new ResponseEntity<>("Invalid data request", HttpStatus.BAD_REQUEST);
        }

        Optional<Order> maybeOrder = orderService.findOrderById(idOrder);

        if(! maybeOrder.isPresent()){
            return new ResponseEntity<>("Invalid data request", HttpStatus.NOT_FOUND);
        }

        if(maybeOrder.get().getIdClient() != idClient){
            return new ResponseEntity<>("This order does not belong to you", HttpStatus.UNAUTHORIZED);
        }

        if(! maybeOrder.get().isStatePending()){
            return new ResponseEntity<>("Menu cannot be cancelled", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(orderService.cancelOrder(maybeOrder.get()),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/order/rate")
    public ResponseEntity rateOrder(@RequestBody HashMap<String,String> body) {

        String googleId = body.get("googleId");
        String tokenAccess = body.get("tokenAccess");
        long idOrder;
        long idClient;
        int rate;

        if(! googleAuthService.clientHasAccess(googleId,tokenAccess)){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        try{
            idClient = Long.parseLong(body.get("idClient"));
            idOrder = Long.parseLong(body.get("idOrder"));
            rate = Integer.parseInt(body.get("rate"));
        }catch (Exception e){
            return new ResponseEntity<>("Invalid data request", HttpStatus.BAD_REQUEST);
        }

        if(rate > 5 || rate <= 0 ){
            return new ResponseEntity<>("Rate must be positive and less or equals five stars", HttpStatus.BAD_REQUEST);
        }

        Optional<Order> maybeOrder = orderService.findOrderById(idOrder);

        if(! maybeOrder.isPresent()){
            return new ResponseEntity<>("Invalid data request", HttpStatus.NOT_FOUND);
        }

        if(maybeOrder.get().getIdClient() != idClient){
            return new ResponseEntity<>("This order does not belong to you", HttpStatus.UNAUTHORIZED);
        }

        if(! maybeOrder.get().isCanBeRated()){
            return new ResponseEntity<>("Order cannot be rated or is already rated", HttpStatus.BAD_REQUEST);
        }

        //transactional
        Order orderSaved = orderService.rateOrder(maybeOrder.get(),rate);

        //Send email to provider if menu is cancelled by bad average rank and he is penalized.
        if(orderSaved.getMenu().isCancelledState() && orderSaved.getMenu().getProvider().isPenalized()){

            emailSenderService.sendProviderMenuIsCancelledAndHeIsPenalized(orderSaved.getMenu());
        }

        //Send email to provider if menu is cancelled by bad average rank and he is penalized.
        if(orderSaved.getMenu().isCancelledState() && ! orderSaved.getMenu().getProvider().isPenalized()){

            emailSenderService.sendProviderMenuIsCancelledByBadAverageRankAndOwnAStrike(orderSaved.getMenu());
        }

        return new ResponseEntity<>(orderSaved,HttpStatus.OK);
    }

    private boolean notEnoughCredit(Credit credit, Menu menu, Integer menusAmount) {
        return ! credit.isGreaterOrEqual(new Credit(menu.priceWithAmount(menusAmount)));
    }

}
