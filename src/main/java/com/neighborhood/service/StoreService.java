package com.neighborhood.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neighborhood.model.Store;
import com.neighborhood.repository.StoreRepository;

@Service
@Transactional
public class StoreService {
  @Autowired
  StoreRepository storeRepository;

  public void newStore(Store store) {
    storeRepository.save(store);
  }

  public void updateStore(Store store) {
    storeRepository.save(store);
  }

  public void deleteStore(String storeId) {
    storeRepository.deleteById(storeId);
  }

  public List<Store> findAll() {
    return storeRepository.findAll();
  }

  public List<Store> searchStore(String query) {
    return storeRepository.searchStore(query);
  }

  public Store findById(String storeId) {
    Optional<Store> store = storeRepository.findById(storeId);

    if (!store.isPresent()) {
      return null;
    }

    return storeRepository.findById(storeId).get();
  }
}
