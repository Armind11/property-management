package com.mycompany.property_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PROPERTY_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "PROPERTY_TITLE", nullable = false)
    private String title;
    private String description;
    private double price;
    private String address;
    @ManyToOne(fetch = FetchType.LAZY) //it will not fetch the User data while fetching a property.
    @JoinColumn(name = "USER_ID" ,nullable = false)
    private  UserEntity userEntity;
}
