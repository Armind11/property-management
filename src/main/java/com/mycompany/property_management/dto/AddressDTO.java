package com.mycompany.property_management.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {

    private Long id;
    private String houseNo;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private Long userId;
}
