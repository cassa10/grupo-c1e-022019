package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "menuController")
public class MenuController {

    @Autowired
    private MenuService menuService = new MenuService();

    @Autowired
    private ProviderService providerService = new ProviderService();

    @RequestMapping(method = RequestMethod.POST, value = "/menu")
    public ResponseEntity postMenu(@RequestBody MenuDTO menuDTO){

        if(! providerService.providerExists(menuDTO.getIdProvider())){
            return new ResponseEntity<>("Provider does not exist", HttpStatus.NOT_FOUND);
        }

        if( menuService.existsMenuWithSameName(menuDTO.getName())){
            return new ResponseEntity<>("Please, choose a different name", HttpStatus.BAD_REQUEST);
        }

        Provider provider = providerService.getProvider(menuDTO.getIdProvider());

        Menu newMenu = MenuBuilder.aMenu()
                .withProvider(provider)
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
            provider.addMenu(newMenu);
            menuService.saveMenu(newMenu);

            return new ResponseEntity<>(new MenuDTO(newMenu), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/menu/{idMenu}")
    public ResponseEntity deleteMenu(@PathVariable long idMenu){

        if(!menuService.existMenu(idMenu)){
            return new ResponseEntity<>("Menu not found",HttpStatus.NOT_FOUND);
        }

        menuService.delete(idMenu);

        return new ResponseEntity<>("Menu successfully removed",HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/name")
    public ResponseEntity searchMenuByName(@RequestBody HashMap<String,String> body){
        List<Menu> values = new ArrayList<>();

        values = menuService.searchByName(body.get("name"),body.get("priceOrder"),body.get("rankOrder"));

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/category")
    public ResponseEntity searchMenuByCategory(@RequestBody HashMap<String,String> body){
        List<Menu> values = new ArrayList<>();

        values = menuService.searchByCategory(CategoryMenu.valueOf(body.get("category")),body.get("priceOrder"),body.get("rankOrder"));

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/city")
    public ResponseEntity searchMenuByProviderCity(@RequestBody HashMap<String,String> body){
        List<Menu> values = new ArrayList<>();

        values = menuService.searchByProviderCity(body.get("city"),body.get("priceOrder"),body.get("rankOrder"));

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/name_category")
    public ResponseEntity searchMenuByNameAndCategory(@RequestBody HashMap<String,String> body){
        List<Menu> values;

        values = menuService.searchByNameAndCategory(body.get("name"),CategoryMenu.valueOf(body.get("category")),body.get("priceOrder"),body.get("rankOrder"));

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/name_city")
    public ResponseEntity searchMenuByNameAndCity(@RequestBody HashMap<String,String> body){
        List<Menu> values;

        values = menuService.searchByNameAndCity(body.get("name"),body.get("city"),body.get("priceOrder"),body.get("rankOrder"));

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/category_city")
    public ResponseEntity searchMenuByCategoryAndCity(@RequestBody HashMap<String,String> body){
        List<Menu> values;

        values = menuService.searchByCategoryAndCity(CategoryMenu.valueOf(body.get("category")),body.get("city"),body.get("priceOrder"),body.get("rankOrder"));

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/search/name_category_city")
    public ResponseEntity searchMenuByNameAndCategoryAndCity(@RequestBody HashMap<String,String> body){
        List<Menu> values;

        values = menuService.searchByNameAndCategoryAndCity(body.get("name"),CategoryMenu.valueOf(body.get("category")),body.get("city"),body.get("priceOrder"),body.get("rankOrder"));

        return new ResponseEntity<>(values,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/menu/top_ranked")
    public ResponseEntity getMenusTopRanked(){

        return new ResponseEntity<>(menuService.getMenusSortedByMaxRank(),HttpStatus.OK);
    }

}
