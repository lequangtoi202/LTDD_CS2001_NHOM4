package com.quangtoi.flowerstore.repository;

import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "SELECT c.* FROM flower_store.carts c inner join accounts a on a.cart_id=c.id where a.id =:id", nativeQuery = true)
    Cart getByAccId(@Param("id") Long id);
}
