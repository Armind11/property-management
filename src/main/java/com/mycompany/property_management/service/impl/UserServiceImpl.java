package com.mycompany.property_management.service.impl;

import com.mycompany.property_management.converter.UserConverter;
import com.mycompany.property_management.dto.UserDTO;
import com.mycompany.property_management.entity.AddressEntity;
import com.mycompany.property_management.entity.UserEntity;
import com.mycompany.property_management.exception.BusinessException;
import com.mycompany.property_management.exception.ErrorModel;
import com.mycompany.property_management.repository.AddressRepository;
import com.mycompany.property_management.repository.UserRepository;
import com.mycompany.property_management.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService {


    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final AddressRepository addressRepository;

    public UserServiceImpl (UserRepository userRepository, UserConverter userConverter, AddressRepository addressRepository){
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.addressRepository = addressRepository;
    }

    @Override
    public UserDTO register(UserDTO userDTO) {

        Optional<UserEntity> optUe = userRepository.findByOwnerEmail(userDTO.getOwnerEmail());
        if(optUe.isPresent()) {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("EMAIL_ALREADY_EXISST");
            errorModel.setMessage("The Email You Are Trying To Register Already exist !");
            errorModelList.add(errorModel);

            // on peut crée d'autres type d'erreurs en fonction du scénario metier
            // ICI ----->

            // ensuite on leve l'exception de la liste d'erreur
            throw new BusinessException(errorModelList);
        }
            UserEntity userEntity = userConverter.convertDTOtoEntity(userDTO);
            userEntity = userRepository.save(userEntity);

            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setHouseNo(userDTO.getHouseNo());
            addressEntity.setCity(userDTO.getCity());
            addressEntity.setPostalCode(userDTO.getPostalCode());
            addressEntity.setStreet(userDTO.getStreet());
            addressEntity.setCountry(userDTO.getCountry());
            addressEntity.setUserEntity(userEntity);

            addressRepository.save(addressEntity);

            userDTO = userConverter.convertEntitytoDTO(userEntity);

            return  userDTO;
    }

    @Override
    public UserDTO login(String email, String password) {
        UserDTO userDTO = null;
        Optional<UserEntity> optionalUserEntity = userRepository.findByOwnerEmailAndPassword(email,password);
        if (optionalUserEntity.isPresent())
        {
            userDTO = userConverter.convertEntitytoDTO(optionalUserEntity.get());
        }else{
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("INVALIDE_LOGIN");
            errorModel.setMessage("Incorrect Email or Password");
            errorModelList.add(errorModel);

            // on peut crée d'autres type d'erreurs en fonction du scénario metier
            // ICI ----->

            // ensuite on leve l'exception de la liste d'erreur
            throw new BusinessException(errorModelList);
        }
        return userDTO;
    }
}
