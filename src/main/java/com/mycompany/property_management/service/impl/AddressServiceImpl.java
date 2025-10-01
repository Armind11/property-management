package com.mycompany.property_management.service.impl;

import com.mycompany.property_management.converter.AddressConverter;
import com.mycompany.property_management.dto.AddressDTO;
import com.mycompany.property_management.entity.AddressEntity;
import com.mycompany.property_management.exception.BusinessException;
import com.mycompany.property_management.exception.ErrorModel;
import com.mycompany.property_management.repository.AddressRepository;
import com.mycompany.property_management.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressConverter addressConverter;


    @Override
    public AddressDTO findAddressByUserId(Long userId) {
        AddressEntity entity = addressRepository.findAddressByUserEntityId(userId)
                .orElseThrow(() -> businessException(
                        "ADDRESS_NOT_FOUND",
                        "No address found for user id " + userId
                ));

        return addressConverter.convertEntityToDTO(entity);
    }

    private BusinessException businessException(String code, String message) {
        ErrorModel err = new ErrorModel();
        err.setCode(code);
        err.setMessage(message);
        return new BusinessException(List.of(err));
    }
}
