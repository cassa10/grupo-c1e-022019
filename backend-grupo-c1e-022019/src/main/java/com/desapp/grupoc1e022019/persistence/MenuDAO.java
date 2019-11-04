package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Menu> findAllLikeNameSort(String value,String orderPrice,String orderRank){
       return menuRepository.findAllLikeName(value,getApropiateSort(orderPrice,orderRank));
    }

    public List<Menu> findAllContainCategory(CategoryMenu value, String orderPrice, String orderRank){
        return menuRepository.findAllContainCategory(value,getApropiateSort(orderPrice,orderRank));
    }

    public List<Menu> findAllByProviderCity(String value,String orderPrice,String orderRank){
        return menuRepository.findAllByProviderCity(value,getApropiateSort(orderPrice,orderRank));
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

    public List<Menu> findAllByNameAndCategory(String name, CategoryMenu category, String priceOrder, String rankOrder) {
        return menuRepository.findAllLikeNameAndCategory(name,category,getApropiateSort(priceOrder,rankOrder));
    }

    public List<Menu> findAllByNameAndCity(String name, String city, String priceOrder, String rankOrder) {
        return menuRepository.findAllLikeNameAndCity(name,city,getApropiateSort(priceOrder,rankOrder));
    }

    public List<Menu> findAllByCategoryAndCity(CategoryMenu category, String city, String priceOrder, String rankOrder) {
        return menuRepository.findAllByCategoryAndCity(category,city,getApropiateSort(priceOrder,rankOrder));
    }

    public List<Menu> findAllLikeNameAndCategoryAndCity(String name, CategoryMenu category, String city, String priceOrder, String rankOrder) {
        return menuRepository.findAllLikeNameAndCategoryAndCity(name,category,city,getApropiateSort(priceOrder,rankOrder));
    }

    public List<Menu> getMenusSortedByMaxRank(){
        return menuRepository.findAll(new Sort(Sort.Direction.DESC,"menuRank.ratingSum"));
    }
}
