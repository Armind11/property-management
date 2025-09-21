package com.mycompany.property_management.service.impl;

import com.mycompany.property_management.converter.PropertyConverter;
import com.mycompany.property_management.dto.PropertyDTO;
import com.mycompany.property_management.entity.PropertyEntity;
import com.mycompany.property_management.repository.PropertyRepository;
import com.mycompany.property_management.service.PropertyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyConverter propertyConverter;

    @Value("${pms.dummy:}")
    private String dummy;

    @Value("${spring.datasource.url:}")
    private String dbUrl;

    // ✅ Constructor Injection
    public PropertyServiceImpl(PropertyRepository propertyRepository,
                               PropertyConverter propertyConverter) {
        this.propertyRepository = propertyRepository;
        this.propertyConverter = propertyConverter;
    }

    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO) {
        PropertyEntity pe = propertyConverter.convertDTOtoEntity(propertyDTO);
        pe = propertyRepository.save(pe);
        return propertyConverter.convertEntityToDTO(pe);
    }

    @Override
    public List<PropertyDTO> getAllProperties() {

        List<PropertyEntity> listOfProps = (List<PropertyEntity>) propertyRepository.findAll();
        List<PropertyDTO> propList = new ArrayList<>();

        for (PropertyEntity pe : listOfProps) {
            PropertyDTO dto = propertyConverter.convertEntityToDTO(pe);
            propList.add(dto);
        }
        return propList;
    }

    @Override
    public PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyId) {
        return propertyRepository.findById(propertyId)
                .map(pe -> {
                    pe.setTitle(propertyDTO.getTitle());
                    pe.setAddress(propertyDTO.getAddress());
                    pe.setPrice(propertyDTO.getPrice());
                    pe.setDescription(propertyDTO.getDescription());
                    return propertyConverter.convertEntityToDTO(propertyRepository.save(pe));
                })
                .orElse(new PropertyDTO());
    }

    @Override
    public PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyId) {
        return propertyRepository.findById(propertyId)
                .map(pe -> {
                    pe.setDescription(propertyDTO.getDescription());
                    return propertyConverter.convertEntityToDTO(propertyRepository.save(pe));
                })
                .orElse(new PropertyDTO());
    }

    @Override
    public PropertyDTO updatePropertyPrice(PropertyDTO propertyDTO, Long propertyId) {
        return propertyRepository.findById(propertyId)
                .map(pe -> {
                    pe.setPrice(propertyDTO.getPrice());
                    return propertyConverter.convertEntityToDTO(propertyRepository.save(pe));
                })
                .orElse(new PropertyDTO());
    }

    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}
