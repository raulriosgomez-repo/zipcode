package com.example.demo.service;

import java.util.List;

import com.example.demo.vo.Address;

public interface AddressService {
	
	public List<Address> getAddress(String zipCode);

}
