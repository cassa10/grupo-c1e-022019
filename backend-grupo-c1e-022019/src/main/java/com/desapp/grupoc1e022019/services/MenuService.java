package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.model.menuComponents.menuState.CancelledMenu;
import com.desapp.grupoc1e022019.persistence.MenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Scope(value = "session")
@Component(value = "menuService")
public class MenuService {

    @Autowired
    private MenuDAO menuDAO = new MenuDAO();

    @Transactional
    public Menu addAndSaveMenu(Provider provider, Menu newMenu) {
        provider.addMenu(newMenu);
        return menuDAO.save(newMenu);
    }

    public boolean existsMenuWithSameName(String name) {
        return menuDAO.existsMenuWithSameName(name);
    }

    public boolean existMenu(long idMenu) {
        return menuDAO.existsMenu(idMenu);
    }

    public Page<Menu> searchByName(String value, String priceOrder, String rankOrder, int fromPage, int sizePage) {
        return menuDAO.findAllLikeNameSort(value,priceOrder,rankOrder,fromPage,sizePage);
    }

    public Page<Menu> searchByCategory(CategoryMenu value, String priceOrder, String rankOrder, int fromPage, int sizePage) {
        return menuDAO.findAllContainCategory(value,priceOrder,rankOrder,fromPage,sizePage);
    }

    public Page<Menu> searchByProviderCity(String value, String priceOrder, String rankOrder, int fromPage, int sizePage) {
        return menuDAO.findAllByProviderCity(value,priceOrder,rankOrder,fromPage,sizePage);
    }

    public Page<Menu> searchByNameAndCategory(String name, CategoryMenu category, String priceOrder, String rankOrder, int fromPage, int sizePage) {
        return menuDAO.findAllByNameAndCategory(name,category,priceOrder,rankOrder,fromPage,sizePage);
    }

    public Page<Menu> searchByNameAndCity(String name, String city, String priceOrder, String rankOrder, int fromPage, int sizePage) {
        return menuDAO.findAllByNameAndCity(name,city,priceOrder,rankOrder,fromPage,sizePage);
    }

    public Page<Menu> searchByCategoryAndCity(CategoryMenu category, String city, String priceOrder, String rankOrder, int fromPage, int sizePage) {
        return menuDAO.findAllByCategoryAndCity(category,city,priceOrder,rankOrder,fromPage,sizePage);
    }

    public Page<Menu> searchByNameAndCategoryAndCity(String name, CategoryMenu category, String city, String priceOrder, String rankOrder, int fromPage, int sizePage) {
        return menuDAO.findAllLikeNameAndCategoryAndCity(name,category,city,priceOrder,rankOrder,fromPage,sizePage);
    }

    public List<Menu> getMenusSortedByMaxRank() {
        return menuDAO.getMenusSortedByMaxRank();
    }

    public Menu getMenu(long idMenu) {
        return menuDAO.getMenu(idMenu);
    }

    public Optional<Menu> findMenuById(long id) {
        return menuDAO.findMenuById(id);
    }

    @Transactional
    public Menu cancelMenu(Menu menu) {
        menu.setMenuState(new CancelledMenu());

        return menuDAO.save(menu);
    }
}
