package com.mycompany.property_management.converter;
import com.mycompany.property_management.dto.AddressDTO;
import com.mycompany.property_management.entity.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {

    public AddressDTO convertEntityToDTO(AddressEntity addressEntity){

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(addressEntity.getId());
        addressDTO.setCity(addressEntity.getCity());
        addressDTO.setCountry(addressEntity.getCountry());
        addressDTO.setHouseNo(addressEntity.getHouseNo());
        addressDTO.setStreet(addressEntity.getStreet());
        addressDTO.setPostalCode(addressEntity.getPostalCode());
        addressDTO.setUserId(addressEntity.getUserEntity().getId());
        return addressDTO;
    }
}
