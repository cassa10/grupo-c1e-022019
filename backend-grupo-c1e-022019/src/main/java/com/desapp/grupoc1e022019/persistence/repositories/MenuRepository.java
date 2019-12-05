package com.desapp.grupoc1e022019.persistence.repositories;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {

    List<Menu> findByName(String name);

    @Query("SELECT m FROM Menu m WHERE m.menuState.class != ?2 AND lower(m.name) LIKE lower(concat('%',?1,'%'))")
    Page<Menu> findAllLikeNameAndWithoutState(String value, String menuState, Pageable pageable);

    @Query("SELECT m FROM Menu m WHERE m.menuState.class != ?2 AND ?1 member of m.categories")
    Page<Menu> findAllContainCategoryAndWithoutState(CategoryMenu category,String menuState, Pageable pageable);

    @Query("SELECT m FROM Menu m WHERE m.menuState.class != ?2 AND lower(m.provider.city) = lower(?1)")
    Page<Menu> findAllByProviderCityAndWithoutState(String location, String menuState, Pageable pageable);

    @Query("SELECT m FROM Menu m WHERE m.menuState.class != ?3 AND lower(m.name) LIKE lower(concat('%',?1,'%')) AND (?2 member of m.categories)")
    Page<Menu> findAllLikeNameAndCategoryAndWithoutState(String name, CategoryMenu category, String menuState, Pageable pageable);

    @Query("SELECT m FROM Menu m WHERE m.menuState.class != ?3 AND ?1 member of m.categories AND lower(m.provider.city) = lower(?2)")
    Page<Menu> findAllByCategoryAndCityAndWithoutState(CategoryMenu category, String city, String menuState, Pageable pageable);

    @Query("SELECT m FROM Menu m WHERE m.menuState.class != ?3 AND m.name LIKE %?1% AND lower(m.provider.city) = lower(?2)")
    Page<Menu> findAllLikeNameAndCityAndWithoutState(String name, String city, String menuState, Pageable pageable);

    @Query("SELECT m FROM Menu m WHERE m.menuState.class != ?4 AND lower(m.name) LIKE lower(concat('%',?1,'%')) AND lower(m.provider.city) = lower(?3) AND ?2 member of m.categories")
    Page<Menu> findAllLikeNameAndCategoryAndCityAndWithoutState(String name, CategoryMenu category, String city, String menuState, Pageable pageable);

    List<Menu> findAllByProvider(Provider providerRecovered);

    @Query("SELECT avg(m.menuRank.rankAverage) FROM Menu m WHERE m.provider = ?2 AND m.menuState.class != ?1 AND m.menuRank.ratingsAmount > 0")
    Double getProviderAverageMenuRankWithoutState(String menuState, Provider provider);
}
