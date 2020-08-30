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

@RestController
@RequestMapping (value = "/zip-codes")
public class ZipCodesController {
	
	@Autowired
	private AddressService addressService;

    @GetMapping(value = "/{zipCode}", produces = "application/hal+json;charset=utf8")
    public ResponseEntity<List<Address>> getProduct(@PathVariable("zipCode") String zipCode) {
        List<Address> addresses = addressService.getAddress(zipCode);
        System.out.println(addresses.size());
        if (addresses.size() == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addresses);
    }
}
