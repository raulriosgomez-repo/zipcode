package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AddressEntity;


@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

	public List<AddressEntity> findByzipcode(String zip_code);
}
