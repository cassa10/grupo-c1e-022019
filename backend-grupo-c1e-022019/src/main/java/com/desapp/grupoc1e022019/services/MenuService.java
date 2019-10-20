package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.persistence.MenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "menuService")
public class MenuService {

    @Autowired
    private MenuDAO menuDAO = new MenuDAO();

    public void saveMenu(Menu newMenu) {
        menuDAO.save(newMenu);
    }

    public boolean existsMenuWithSameName(String name) {
        return menuDAO.existsMenuWithSameName(name);
    }
}
