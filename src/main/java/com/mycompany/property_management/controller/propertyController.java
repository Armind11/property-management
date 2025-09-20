package com.mycompany.property_management.controller;

import com.mycompany.property_management.dto.PropertyDTO;
import com.mycompany.property_management.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class propertyController {

    @Value("${pms.dummy:}")
    private String dummy;

    @Value("${spring.datasource.url:}")
    private String dbUrl;

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello";
    }

    @PostMapping("/properties")
    public ResponseEntity<PropertyDTO> saveProperty(@RequestBody() PropertyDTO propertyDTO) {

        propertyDTO = propertyService.saveProperty(propertyDTO);

        ResponseEntity<PropertyDTO> responseEntity = new ResponseEntity<>(propertyDTO, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping("/properties")
    public ResponseEntity<List<PropertyDTO>> getAllProperties(){
        System.out.println(dummy);
        System.out.println(dbUrl);
        List<PropertyDTO> propertyList = propertyService.getAllProperties();
        ResponseEntity<List<PropertyDTO>> responseEntity = new ResponseEntity<>(propertyList,HttpStatus.OK);
        return responseEntity;
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
    public ResponseEntity<Void> DeleteProperty(@PathVariable Long id){
        propertyService.DeleteProperty(id);
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }
}
