package com.mycompany.property_management.service;

import com.mycompany.property_management.dto.AddressDTO;

import java.util.Optional;

public interface AddressService {

   AddressDTO findAddressByUserId(Long userId);
}
