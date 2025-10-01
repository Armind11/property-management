package com.mycompany.property_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;
    private String ownerName;
    @NotNull(message = "Owner Email is Mandatory")
    @NotEmpty(message = "Owner Email Cannot Be Empty")
    @Size(min = 1,max = 50, message = "Owner Email Must Be Between 1 to 50 Characters in length")
    private String ownerEmail;
    private String phone;
    @NotNull(message = "Password Cannot Be Null")
    @NotEmpty(message = "Password Cannot Be Empty")
    private String password;

    private String houseNo;
    private String street;
    private String city;
    private String postalCode;
    private String country;
}
