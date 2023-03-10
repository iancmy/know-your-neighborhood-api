package com.neighborhood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neighborhood.model.Store;

public interface StoreRepository extends JpaRepository<Store, String> {
  @Query(value = "SELECT * FROM store WHERE store_name LIKE %:query% OR location LIKE %:query%", nativeQuery = true)
  List<Store> searchStore(@Param("query") String query);
}
