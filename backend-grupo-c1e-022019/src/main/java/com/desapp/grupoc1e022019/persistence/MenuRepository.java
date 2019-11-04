package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {

    List<Menu> findByName(String name);

    @Query("SELECT m FROM Menu m WHERE lower(m.name) LIKE lower(concat('%',?1,'%'))")
    List<Menu> findAllLikeName(String value, Sort criteriaSort);

    @Query("SELECT m FROM Menu m WHERE ?1 member of m.categories")
    List<Menu> findAllContainCategory(CategoryMenu category, Sort criteriaSort);

    @Query("SELECT m FROM Menu m WHERE lower(m.provider.city) = lower(?1)")
    List<Menu> findAllByProviderCity(String location, Sort criteriaSort);

    @Query("SELECT m FROM Menu m WHERE lower(m.name) LIKE lower(concat('%',?1,'%')) AND (?2 member of m.categories)")
    List<Menu> findAllLikeNameAndCategory(String name,CategoryMenu category,Sort criteriaSort);

    @Query("SELECT m FROM Menu m WHERE ?1 member of m.categories AND lower(m.provider.city) = lower(?2)")
    List<Menu> findAllByCategoryAndCity(CategoryMenu category,String city,Sort criteriaSort);

    @Query("SELECT m FROM Menu m WHERE m.name LIKE %?1% AND lower(m.provider.city) = lower(?2)")
    List<Menu> findAllLikeNameAndCity(String name,String city,Sort criteriaSort);

    @Query("SELECT m FROM Menu m WHERE lower(m.name) LIKE lower(concat('%',?1,'%')) AND lower(m.provider.city) = lower(?3) AND ?2 member of m.categories")
    List<Menu> findAllLikeNameAndCategoryAndCity(String name,CategoryMenu category,String city,Sort criteriaSort);

}
