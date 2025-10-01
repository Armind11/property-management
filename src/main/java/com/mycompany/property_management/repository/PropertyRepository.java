package com.mycompany.property_management.repository;

import com.mycompany.property_management.entity.PropertyEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface PropertyRepository extends CrudRepository<PropertyEntity,Long> {
    List<PropertyEntity> findByUserEntityId(Long userId);
}
