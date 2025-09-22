package com.mycompany.property_management.controller;

import com.mycompany.property_management.dto.PropertyDTO;
import com.mycompany.property_management.service.PropertyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PropertyController {

    @Value("${pms.dummy:}")
    private String dummy;

    @Value("${spring.datasource.url:}")
    private String dbUrl;


    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }


    @GetMapping("/hello")
    public String sayHello(){
        return "Hello";
    }

    @PostMapping("/properties")
    public ResponseEntity<PropertyDTO> saveProperty(@RequestBody() PropertyDTO propertyDTO) {

        propertyDTO = propertyService.saveProperty(propertyDTO);

        return new ResponseEntity<>(propertyDTO, HttpStatus.CREATED);
    }

    @GetMapping("/properties")
    public ResponseEntity<List<PropertyDTO>> getAllProperties(){
        List<PropertyDTO> propertyList = propertyService.getAllProperties();
        return new ResponseEntity<>(propertyList,HttpStatus.OK);
    }

    @PutMapping("/properties/{id}")
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable Long id, @RequestBody PropertyDTO propertyDTO){

        PropertyDTO updatedProperty = propertyService.updateProperty(propertyDTO,id);
        return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
    }

    @PatchMapping("/properties/update-description/{id}")
    public ResponseEntity<PropertyDTO> updatePropertyDescription(@RequestBody  PropertyDTO propertyDTO ,@PathVariable Long id){
        PropertyDTO updatePropertyDescription = propertyService.updatePropertyDescription(propertyDTO,id);
        return new ResponseEntity<> (updatePropertyDescription, HttpStatus.OK);
    }

    @PatchMapping("/properties/update-price/{id}")
    public ResponseEntity<PropertyDTO> updatePropertyPrice(@RequestBody  PropertyDTO propertyDTO ,@PathVariable Long id){
        PropertyDTO updatePropertyPrice = propertyService.updatePropertyPrice(propertyDTO,id);
        return new ResponseEntity<> (updatePropertyPrice, HttpStatus.OK);
    }

    @DeleteMapping("/properties/delete-property/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id){
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
