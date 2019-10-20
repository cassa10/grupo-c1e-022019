package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuDAO {
    @Autowired
    private MenuRepository menuRepository;

    public void save(Menu newMenu) {
        menuRepository.save(newMenu);
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
}
