package com.example.demo.service;

import java.util.List;

import com.example.demo.vo.Address;
import com.example.demo.vo.InfoCP;

public interface AddressService {
	
	public List<Address> getAddress(String zipCode);

	public InfoCP getAddressByWebService(String zipCode);

	public List<Address> getAddressEntity(String zipCode);

}
