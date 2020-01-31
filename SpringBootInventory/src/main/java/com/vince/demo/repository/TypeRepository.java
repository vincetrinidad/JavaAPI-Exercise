package com.vince.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.vince.demo.model.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, String>{
	
	Type findByname(String type_name);

}
