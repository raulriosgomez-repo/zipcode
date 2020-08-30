package com.example.demo.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Address {

	private String zip_code;
	private String locality;
	private String federal_entity;
	private List<Settlement> settlements;
	private String municipality;
}
