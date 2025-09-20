package com.mycompany.property_management.service.impl;
import com.mycompany.property_management.converter.PropertyConverter;
import com.mycompany.property_management.dto.PropertyDTO;
import com.mycompany.property_management.entity.PropertyEntity;
import com.mycompany.property_management.repository.PropertyRepository;
import com.mycompany.property_management.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertyConverter propertyConverter;

    @Value("${pms.dummy:}")
    private String dummy;

    @Value("${spring.datasource.url:}")
    private String dbUrl;

    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO)
    {
        // 1. Mapper DTO -> Entity
        PropertyEntity pe = propertyConverter.convertDTOtoEntity(propertyDTO);
        pe = propertyRepository.save(pe);
        propertyDTO = propertyConverter.convertEntityToDTO(pe);
        return  propertyDTO;
    }

    @Override
    public List<PropertyDTO> getAllProperties()
    {
        System.out.println("this is inside Service " + dummy);
        System.out.println("this is inside Service " + dbUrl);
        List<PropertyEntity> listOfProps = (List<PropertyEntity>)propertyRepository.findAll();
        List<PropertyDTO> propList = new ArrayList<>();

        for( PropertyEntity pe : listOfProps)
        {
            PropertyDTO dto = propertyConverter.convertEntityToDTO(pe);
            propList.add(dto);
        }
        return propList;
    }

    @Override
    public PropertyDTO updateProperty(PropertyDTO propertyDTO,Long propertyId)
    {
        Optional<PropertyEntity> optEn = propertyRepository.findById(propertyId);
        PropertyDTO propertyUpdatedDTO = new PropertyDTO();
        if(optEn.isPresent())
        {
           PropertyEntity pe = optEn.get();
           pe.setTitle(propertyDTO.getTitle());
           pe.setAddress(propertyDTO.getAddress());
           pe.setPrice(propertyDTO.getPrice());
           pe.setDescription(propertyDTO.getDescription());
           pe = propertyRepository.save(pe);
           propertyUpdatedDTO = propertyConverter.convertEntityToDTO(pe);

        }
        return propertyUpdatedDTO;
    }

    @Override
    public PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyId) {
        Optional<PropertyEntity> optEn = propertyRepository.findById(propertyId);
        PropertyDTO propertyUpdatedDTO = new PropertyDTO();
        if(optEn.isPresent())
        {
            PropertyEntity pe = optEn.get();
            pe.setDescription(propertyDTO.getDescription());
            pe = propertyRepository.save(pe);
            propertyUpdatedDTO = propertyConverter.convertEntityToDTO(pe);

        }
        return propertyUpdatedDTO;
    }

    @Override
    public PropertyDTO updatePropertyPrice(PropertyDTO propertyDTO, Long propertyId) {
        Optional<PropertyEntity> optEn = propertyRepository.findById(propertyId);
        PropertyDTO propertyUpdatedDTO = new PropertyDTO();
        if(optEn.isPresent())
        {
            PropertyEntity pe = optEn.get();
            pe.setPrice(propertyDTO.getPrice());
            pe = propertyRepository.save(pe);
            propertyUpdatedDTO = propertyConverter.convertEntityToDTO(pe);

        }
        return propertyUpdatedDTO;
    }

    @Override
    public void DeleteProperty(Long id)
    {
            propertyRepository.deleteById(id);
    }
}