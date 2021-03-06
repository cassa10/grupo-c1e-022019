package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.model.menuComponents.menuState.CancelledMenu;
import com.desapp.grupoc1e022019.persistence.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MenuDAO {

    @Autowired
    private MenuRepository menuRepository;

    public Menu save(Menu newMenu) {
        return menuRepository.save(newMenu);
    }

    public boolean existsMenuWithSameName(String name) {
        return !menuRepository.findByName(name).isEmpty();
    }

    public boolean existsMenu(long idMenu) {
        return menuRepository.findById(idMenu).isPresent();
    }

    public Page<Menu> findAllLikeNameSort(String value, String orderPrice, String orderRank,String priority, int fromPage, int sizePage){
       return menuRepository.findAllLikeNameAndWithoutState(value, new CancelledMenu().toString(),PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank,priority)));
    }

    public Page<Menu> findAllContainCategory(CategoryMenu value, String orderPrice, String orderRank,String priority, int fromPage, int sizePage){
        return menuRepository.findAllContainCategoryAndWithoutState(value, new CancelledMenu().toString(),PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank,priority)));
    }

    public Page<Menu> findAllByProviderCity(String value,String orderPrice,String orderRank, String priority, int fromPage, int sizePage){
        return menuRepository.findAllByProviderCityAndWithoutState(value,new CancelledMenu().toString(),PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank,priority)));
    }

    public Page<Menu> findAllByNameAndCategory(String name, CategoryMenu category, String orderPrice, String orderRank,String priority, int fromPage, int sizePage) {
        return menuRepository.findAllLikeNameAndCategoryAndWithoutState(name,category,new CancelledMenu().toString(),PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank,priority)));
    }

    public Page<Menu> findAllByNameAndCity(String name, String city, String orderPrice, String orderRank,String priority, int fromPage, int sizePage) {
        return menuRepository.findAllLikeNameAndCityAndWithoutState(name,city,new CancelledMenu().toString(),PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank,priority)));
    }

    public Page<Menu> findAllByCategoryAndCity(CategoryMenu category, String city, String orderPrice, String orderRank,String priority, int fromPage, int sizePage) {
        return menuRepository.findAllByCategoryAndCityAndWithoutState(category,city,new CancelledMenu().toString(),PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank,priority)));
    }

    public Page<Menu> findAllLikeNameAndCategoryAndCity(String name, CategoryMenu category, String city, String orderPrice, String orderRank,String priority, int fromPage, int sizePage) {
        return menuRepository.findAllLikeNameAndCategoryAndCityAndWithoutState(name,category,city,new CancelledMenu().toString(),PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank,priority)));
    }

    public List<Menu> getMenusSortedByMaxRank(){
        return menuRepository.findAll(new Sort(Sort.Direction.DESC,"menuRank.ratingSum"));
    }

    private Sort getApropiateSort(String priceOrder,String rankOrder,String priority){
        //DEFAULT MIN TO MAX PRICE
        Sort sortPrice = new Sort(Sort.Direction.ASC,"menuPriceCalculator.price");

        //DEFAULT MIN TO MAX RANK
        Sort sortRank = new Sort(Sort.Direction.ASC,"menuRank.rankAverage");

        if(priceOrder.equals("max")){
            sortPrice = new Sort(Sort.Direction.DESC,"menuPriceCalculator.price");
        }
        if(rankOrder.equals("max")){
            sortRank = new Sort(Sort.Direction.DESC,"menuRank.rankAverage");
        }
        if(priority.equals("rank")){
            return sortRank;
        }
        return sortPrice;
    }

    public Optional<Menu> getMenu(long idMenu) {
        return menuRepository.findById(idMenu);
    }

    public List<Menu> findAllByProvider(Provider providerRecovered) {
        return menuRepository.findAllByProvider(providerRecovered);
    }

    public void cancelAndSaveMenus(List<Menu> providerMenus) {
        for(Menu menu: providerMenus){
            menu.setMenuState(new CancelledMenu());
            menuRepository.save(menu);
        }
    }

    public Optional<Menu> findMenuById(long id) {
        return menuRepository.findById(id);
    }

    public double getProviderRankReputation(Provider provider) {

        if (menuRepository.providerHasAnyRankAverage(new CancelledMenu().toString(), provider).isEmpty()) {
            return 0d;
        }
        return menuRepository.getProviderAverageMenuRankWithoutState(new CancelledMenu().toString(), provider);
    }
}
