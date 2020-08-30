package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class ResponseCP {

	private String cp;
	private String tipo_asentamiento;
	private String municipio;
	private String estado;
	private String ciudad;
	private String pais;
	private String[] asentamiento;
}
