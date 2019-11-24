package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.exception.MaximumMenusSizeException;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.services.GoogleAuthService;
import com.desapp.grupoc1e022019.services.MenuService;
import com.desapp.grupoc1e022019.services.ProviderService;
import com.desapp.grupoc1e022019.services.builder.MenuBuilder;
import com.desapp.grupoc1e022019.services.dtos.MenuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "menuController")
public class MenuController {

    @Autowired
    private GoogleAuthService googleAuthService = new GoogleAuthService();

    @Autowired
    private MenuService menuService = new MenuService();

    @Autowired
    private ProviderService providerService = new ProviderService();

    @RequestMapping(method = RequestMethod.POST, value = "/menu")
    public ResponseEntity createMenu(@RequestBody MenuDTO menuDTO){

        if(! googleAuthService.providerHasAccess(menuDTO.getGoogleId(),menuDTO.getTokenAccess())){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        Optional<Provider> maybeProvider = providerService.findProviderByGoogleId(menuDTO.getGoogleId());

        if(! maybeProvider.isPresent()){
            return new ResponseEntity<>("Provider does not exist", HttpStatus.NOT_FOUND);
        }

        if(! menuDTO.formMenuIsValid()){
            return new ResponseEntity<>("Request bad data", HttpStatus.BAD_REQUEST);
        }

        if(menuDTO.existMenuWithSameName(maybeProvider.get().getMenus())){
            return new ResponseEntity<>("You own a menu with that name", HttpStatus.BAD_REQUEST);
        }

        menuDTO.trimAllStringValues();

        Menu newMenu = MenuBuilder.aMenu()
                .withProvider(maybeProvider.get())
                .withName(menuDTO.getName())
                .withDescription(menuDTO.getDescription())
                .withCategories(menuDTO.getCategories())
                .withDeliveryValue(menuDTO.getDeliveryValue())
                .withEffectiveDate(menuDTO.getEffectiveDate())
                .withAverageDeliveryTimeInMinutes(menuDTO.getAverageDeliveryTimeInMinutes())
                .withMaxSalesPerDay(menuDTO.getMaxSalesPerDay())
                .withMenuPriceCalculator(menuDTO.getMenuPriceCalculator())
                .build();

        try {
            menuService.addAndSaveMenu(maybeProvider.get(), newMenu);
        }catch (MaximumMenusSizeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newMenu, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/menu/delete")
    public ResponseEntity cancelMenu(@RequestBody MenuDTO menuDTO){

        if(! googleAuthService.providerHasAccess(menuDTO.getGoogleId(),menuDTO.getTokenAccess())){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        Optional<Menu> maybeMenu = menuService.findMenuById(menuDTO.getId());

        if(! maybeMenu.isPresent()){
            return new ResponseEntity<>("Menu not found",HttpStatus.NOT_FOUND);
        }

        Menu menuCanceled = menuService.cancelMenu(maybeMenu.get());

        return new ResponseEntity<>(menuCanceled,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/name/")
    public ResponseEntity searchMenuByName(@RequestParam HashMap<String,String> body){
        String googleId = body.get("googleId");
        String accessToken = body.get("accessToken");

        if(! googleAuthService.clientHasAccess(googleId,accessToken)){
            return new ResponseEntity<>("Please, log in",HttpStatus.UNAUTHORIZED);
        }

        List<Menu> values ;
        int fromPage = Integer.parseInt(body.get("fromPage"));
        int sizePage = Integer.parseInt(body.get("sizePage"));

        values = menuService.searchByName(body.get("name"),body.get("priceOrder"),
                body.get("rankOrder"),fromPage,sizePage).getContent();

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/category/")
    public ResponseEntity searchMenuByCategory(@RequestParam HashMap<String,String> body){
        String googleId = body.get("googleId");
        String accessToken = body.get("accessToken");

        if(! googleAuthService.clientHasAccess(googleId,accessToken)){
            return new ResponseEntity<>("Please, log in",HttpStatus.UNAUTHORIZED);
        }

        List<Menu> values ;
        int fromPage = Integer.parseInt(body.get("fromPage"));
        int sizePage = Integer.parseInt(body.get("sizePage"));

        values = menuService.searchByCategory(CategoryMenu.valueOf(body.get("category")),body.get("priceOrder"),body.get("rankOrder"),fromPage,sizePage).getContent();

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/city/")
    public ResponseEntity searchMenuByProviderCity(@RequestParam HashMap<String,String> body){
        String googleId = body.get("googleId");
        String accessToken = body.get("accessToken");

        if(! googleAuthService.clientHasAccess(googleId,accessToken)){
            return new ResponseEntity<>("Please, log in",HttpStatus.UNAUTHORIZED);
        }

        List<Menu> values ;
        int fromPage = Integer.parseInt(body.get("fromPage"));
        int sizePage = Integer.parseInt(body.get("sizePage"));

        values = menuService.searchByProviderCity(body.get("city"),body.get("priceOrder"),body.get("rankOrder"),fromPage,sizePage).getContent();

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/name_category/")
    public ResponseEntity searchMenuByNameAndCategory(@RequestParam HashMap<String,String> body){
        String googleId = body.get("googleId");
        String accessToken = body.get("accessToken");

        if(! googleAuthService.clientHasAccess(googleId,accessToken)){
            return new ResponseEntity<>("Please, log in",HttpStatus.UNAUTHORIZED);
        }

        List<Menu> values;
        int fromPage = Integer.parseInt(body.get("fromPage"));
        int sizePage = Integer.parseInt(body.get("sizePage"));

        values = menuService.searchByNameAndCategory(body.get("name"),CategoryMenu.valueOf(body.get("category")),body.get("priceOrder"),body.get("rankOrder"),fromPage,sizePage).getContent();

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/name_city/")
    public ResponseEntity searchMenuByNameAndCity(@RequestParam HashMap<String,String> body){
        String googleId = body.get("googleId");
        String accessToken = body.get("accessToken");

        if(! googleAuthService.clientHasAccess(googleId,accessToken)){
            return new ResponseEntity<>("Please, log in",HttpStatus.UNAUTHORIZED);
        }

        List<Menu> values;
        int fromPage = Integer.parseInt(body.get("fromPage"));
        int sizePage = Integer.parseInt(body.get("sizePage"));

        values = menuService.searchByNameAndCity(body.get("name"),body.get("city"),body.get("priceOrder"),body.get("rankOrder"),fromPage,sizePage).getContent();

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/category_city/")
    public ResponseEntity searchMenuByCategoryAndCity(@RequestParam HashMap<String,String> body){
        String googleId = body.get("googleId");
        String accessToken = body.get("accessToken");

        if(! googleAuthService.clientHasAccess(googleId,accessToken)){
            return new ResponseEntity<>("Please, log in",HttpStatus.UNAUTHORIZED);
        }

        List<Menu> values;
        int fromPage = Integer.parseInt(body.get("fromPage"));
        int sizePage = Integer.parseInt(body.get("sizePage"));

        values = menuService.searchByCategoryAndCity(CategoryMenu.valueOf(body.get("category")),body.get("city"),body.get("priceOrder"),body.get("rankOrder"),fromPage,sizePage).getContent();

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/name_category_city/")
    public ResponseEntity searchMenuByNameAndCategoryAndCity(@RequestParam HashMap<String,String> body){
        String googleId = body.get("googleId");
        String accessToken = body.get("accessToken");

        if(! googleAuthService.clientHasAccess(googleId,accessToken)){
            return new ResponseEntity<>("Please, log in",HttpStatus.UNAUTHORIZED);
        }

        List<Menu> values;
        int fromPage = Integer.parseInt(body.get("fromPage"));
        int sizePage = Integer.parseInt(body.get("sizePage"));

        values = menuService.searchByNameAndCategoryAndCity(body.get("name"),
                        CategoryMenu.valueOf(body.get("category")),
                        body.get("city"),
                        body.get("priceOrder"),
                        body.get("rankOrder"),fromPage,sizePage).getContent();

        return new ResponseEntity<>(values,HttpStatus.OK);
    }
}
