package com.mycompany.property_management.controller;

import com.mycompany.property_management.dto.AddressDTO;
import com.mycompany.property_management.dto.PropertyDTO;
import com.mycompany.property_management.service.AddressService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<AddressDTO> getAddressByUserId (@PathVariable ("userId") Long userId)
    {
        AddressDTO addressDTO = addressService.findAddressByUserId(userId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

}
