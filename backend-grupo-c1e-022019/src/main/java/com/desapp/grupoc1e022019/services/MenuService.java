package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.persistence.MenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Scope(value = "session")
@Component(value = "menuService")
public class MenuService {

    @Autowired
    private MenuDAO menuDAO = new MenuDAO();

    @Transactional
    public Menu saveMenu(Menu newMenu) {
        return menuDAO.save(newMenu);
    }

    public boolean existsMenuWithSameName(String name) {
        return menuDAO.existsMenuWithSameName(name);
    }

    public boolean existMenu(long idMenu) {
        return menuDAO.existsMenu(idMenu);
    }

    @Transactional
    public void delete(long idMenu) {
        menuDAO.delete(idMenu);
    }

    public List<Menu> searchByName(String value,String priceOrder,String rankOrder) {
        return menuDAO.findAllLikeNameSort(value,priceOrder,rankOrder);
    }

    public List<Menu> searchByCategory(CategoryMenu value, String priceOrder, String rankOrder) {
        return menuDAO.findAllContainCategory(value,priceOrder,rankOrder);
    }

    public List<Menu> searchByProviderCity(String value, String priceOrder, String rankOrder) {
        return menuDAO.findAllByProviderCity(value,priceOrder,rankOrder);
    }

    public List<Menu> searchByNameAndCategory(String name, CategoryMenu category, String priceOrder, String rankOrder) {
        return menuDAO.findAllByNameAndCategory(name,category,priceOrder,rankOrder);
    }

    public List<Menu> searchByNameAndCity(String name, String city, String priceOrder, String rankOrder) {
        return menuDAO.findAllByNameAndCity(name,city,priceOrder,rankOrder);
    }

    public List<Menu> searchByCategoryAndCity(CategoryMenu category, String city, String priceOrder, String rankOrder) {
        return menuDAO.findAllByCategoryAndCity(category,city,priceOrder,rankOrder);
    }

    public List<Menu> searchByNameAndCategoryAndCity(String name, CategoryMenu category, String city, String priceOrder, String rankOrder) {
        return menuDAO.findAllLikeNameAndCategoryAndCity(name,category,city,priceOrder,rankOrder);
    }

    public List<Menu> getMenusSortedByMaxRank() {
        return menuDAO.getMenusSortedByMaxRank();
    }
}
