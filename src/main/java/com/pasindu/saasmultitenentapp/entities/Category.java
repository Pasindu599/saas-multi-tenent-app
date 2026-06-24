package com.pasindu.saasmultitenentapp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;  
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.pasindu.saasmultitenentapp.common.AbstractEntity;

import jakarta.persistence.Column;
 
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;
    
}
