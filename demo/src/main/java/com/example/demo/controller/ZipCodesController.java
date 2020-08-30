package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AddressService;
import com.example.demo.vo.Address;
import com.example.demo.vo.InfoCP;

@RestController
@RequestMapping (value = "/zip-codes")
public class ZipCodesController {
	
	@Autowired
	private AddressService addressService;

    @GetMapping(value = "/{zipCode}", produces = "application/hal+json;charset=utf8")
    public ResponseEntity<List<Address>> getProduct(@PathVariable("zipCode") String zipCode) {
        List<Address> addresses = addressService.getAddress(zipCode);
        if (addresses.size() == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addresses);
    }
    
    @GetMapping(value = "/ws/{zipCode}", produces = "application/hal+json;charset=utf8")
    public ResponseEntity<InfoCP> getProductByWebService(@PathVariable("zipCode") String zipCode) {
        InfoCP infoCP = addressService.getAddressByWebService(zipCode);
        if (null == infoCP){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(infoCP);
    }
    
    @GetMapping(value = "/db/{zipCode}", produces = "application/hal+json;charset=utf8")
    public ResponseEntity<List<Address>> getProductByDataBase(@PathVariable("zipCode") String zipCode) {
        List<Address> addresses = addressService.getAddressEntity(zipCode);
        if (addresses.size() == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addresses);
    }
}
