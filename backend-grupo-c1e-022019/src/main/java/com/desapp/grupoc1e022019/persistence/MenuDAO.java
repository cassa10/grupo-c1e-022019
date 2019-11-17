package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public void delete(long idMenu) {
        menuRepository.deleteById(idMenu);
    }

    public Page<Menu> findAllLikeNameSort(String value, String orderPrice, String orderRank, int fromPage, int sizePage){
       return menuRepository.findAllLikeName(value, PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank)));
    }

    public Page<Menu> findAllContainCategory(CategoryMenu value, String orderPrice, String orderRank, int fromPage, int sizePage){
        return menuRepository.findAllContainCategory(value, PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank)));
    }

    public Page<Menu> findAllByProviderCity(String value,String orderPrice,String orderRank, int fromPage, int sizePage){
        return menuRepository.findAllByProviderCity(value,PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank)));
    }

    public Page<Menu> findAllByNameAndCategory(String name, CategoryMenu category, String orderPrice, String orderRank, int fromPage, int sizePage) {
        return menuRepository.findAllLikeNameAndCategory(name,category,PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank)));
    }

    public Page<Menu> findAllByNameAndCity(String name, String city, String orderPrice, String orderRank, int fromPage, int sizePage) {
        return menuRepository.findAllLikeNameAndCity(name,city,PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank)));
    }

    public Page<Menu> findAllByCategoryAndCity(CategoryMenu category, String city, String orderPrice, String orderRank, int fromPage, int sizePage) {
        return menuRepository.findAllByCategoryAndCity(category,city,PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank)));
    }

    public Page<Menu> findAllLikeNameAndCategoryAndCity(String name, CategoryMenu category, String city, String orderPrice, String orderRank, int fromPage, int sizePage) {
        return menuRepository.findAllLikeNameAndCategoryAndCity(name,category,city,PageRequest.of(fromPage,sizePage,getApropiateSort(orderPrice,orderRank)));
    }

    public List<Menu> getMenusSortedByMaxRank(){
        return menuRepository.findAll(new Sort(Sort.Direction.DESC,"menuRank.ratingSum"));
    }

    private Sort getApropiateSort(String priceOrder,String rankOrder){
        //DEFAULT MIN TO MAX PRICE
        Sort sortPrice = new Sort(Sort.Direction.ASC,"menuPriceCalculator.price");

        //DEFAULT MIN TO MAX RANK
        Sort sortRank = new Sort(Sort.Direction.ASC,"menuRank.ratingSum");;

        if(priceOrder.equals("max")){
            sortPrice = new Sort(Sort.Direction.DESC,"menuPriceCalculator.price");
        }
        if(rankOrder.equals("max")){
            sortRank = new Sort(Sort.Direction.DESC,"menuRank.ratingSum");;
        }
        return sortPrice.and(sortRank);
    }
}
