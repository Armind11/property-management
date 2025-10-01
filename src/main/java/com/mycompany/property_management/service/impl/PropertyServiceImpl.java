package com.mycompany.property_management.service.impl;

import com.mycompany.property_management.converter.PropertyConverter;
import com.mycompany.property_management.dto.PropertyDTO;
import com.mycompany.property_management.entity.PropertyEntity;
import com.mycompany.property_management.entity.UserEntity;
import com.mycompany.property_management.exception.BusinessException;
import com.mycompany.property_management.exception.ErrorModel;
import com.mycompany.property_management.repository.PropertyRepository;
import com.mycompany.property_management.repository.UserRepository;
import com.mycompany.property_management.service.PropertyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyConverter propertyConverter;
    private final UserRepository userRepository;

    @Value("${pms.dummy:}")
    private String dummy;

    @Value("${spring.datasource.url:}")
    private String dbUrl;

    // ✅ Constructor Injection
    public PropertyServiceImpl(PropertyRepository propertyRepository,
                               PropertyConverter propertyConverter,UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyConverter = propertyConverter;
        this.userRepository = userRepository;
    }

    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO) {

        Optional<UserEntity> optUe = userRepository.findById(propertyDTO.getUserId());
        if (optUe.isPresent()){
            propertyDTO.setUserId(optUe.get().getId());
            PropertyEntity pe = propertyConverter.convertDTOtoEntity(propertyDTO);
            pe.setUserEntity(optUe.get());
            pe = propertyRepository.save(pe);
            return propertyConverter.convertEntityToDTO(pe);
        }else{
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("USER_ID_NOT_EXIST");
            errorModel.setMessage("User Id Does Not Exist");
            errorModelList.add(errorModel);

            // on peut crée d'autres type d'erreurs en fonction du scénario metier
            // ICI ----->

            // ensuite on leve l'exception de la liste d'erreur
            throw new BusinessException(errorModelList);
        }

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
    public List<PropertyDTO> getAllPropertiesForUser(Long userId) {

        // pas de findAll()
        List<PropertyEntity> listOfProps = propertyRepository.findByUserEntityId(userId);
        List<PropertyDTO> propList = new ArrayList<>();
        if(listOfProps.isEmpty())
        {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("NO_PROPERTY_FOUND");
            errorModel.setMessage("User Id Does Not Exist or No property found");
            errorModelList.add(errorModel);

            // on peut crée d'autres type d'erreurs en fonction du scénario metier
            // ICI ----->

            // ensuite on leve l'exception de la liste d'erreur
            throw new BusinessException(errorModelList);
        }else {
            for (PropertyEntity pe : listOfProps) {
                PropertyDTO dto = propertyConverter.convertEntityToDTO(pe);
                propList.add(dto);
            }
            return propList;
        }
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
