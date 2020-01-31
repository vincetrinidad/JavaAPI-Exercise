package com.vince.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vince.demo.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, String>{

}
