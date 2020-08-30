package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TR_ADDRESS")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String zipcode;
	private String locality;
	private String federal_entity;
	private String municipality;
	private String name;
	private String zone_type;
	private String settlement_type;	
}
