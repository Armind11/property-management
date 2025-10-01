package com.mycompany.property_management.repository;


import com.mycompany.property_management.entity.AddressEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<AddressEntity, Long> {

    @Query("SELECT a FROM AddressEntity a WHERE a.userEntity.id = :userId")
    Optional<AddressEntity> findAddressByUserEntityId(@Param("userId") Long userId);
}
