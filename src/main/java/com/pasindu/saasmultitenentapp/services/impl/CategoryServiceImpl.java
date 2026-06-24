package com.pasindu.saasmultitenentapp.services.impl;

import com.pasindu.saasmultitenentapp.requests.CategoryRequest;
import com.pasindu.saasmultitenentapp.responses.CategoryResponse;
import com.pasindu.saasmultitenentapp.services.CategoryService;

import jakarta.persistence.EntityNotFoundException;

import com.pasindu.saasmultitenentapp.entities.Category;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.pasindu.saasmultitenentapp.mappers.CategoryMapper;
import com.pasindu.saasmultitenentapp.repositories.CategoryRepository;
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    @Override
    public void create(CategoryRequest request) {

        // check if the category already exists
        checkIfCategoryExistsByName(request.getName());

        // create the category
        final Category category = categoryMapper.toEntity(request);
        this.categoryRepository.save(category);
    }

   

    @Override
    public void update(String id, CategoryRequest request) {
        final Optional<Category> existingCategory = this.categoryRepository.findById(id);
        if (existingCategory.isEmpty()) {
            log.debug("Category not found with id: {}", id);
            throw new EntityNotFoundException(String.format("Category not found with id: %s", id));
        }

        // check if the category exists
        final Category category = existingCategory.get();

        if (!category.getName().equalsIgnoreCase(request.getName())) {
            checkIfCategoryExistsByName(request.getName());
        }

        // update the category
        final Category updatedCategory = categoryMapper.toEntity(request);
        updatedCategory.setId(id);
        this.categoryRepository.save(updatedCategory);
       


    }

   
    @Override
    public void delete(String id) {
        final Category existingCategory = this.categoryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Category not found with id: %s", id)));

        // delete the category
        // final Category category = existingCategory.get();
       
        // category.setDeleted(true);
        // this.categoryRepository.save(category);
         
        this.categoryRepository.delete(existingCategory);
    }

    @Override
    public CategoryResponse getByID(String id) {
        return this.categoryRepository.findById(id)
        .map(this.categoryMapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Category not found with id: %s", id)));
    }   



    private void checkIfCategoryExistsByName(String name) {
        final Optional<Category> category = this.categoryRepository.findByNameIgnoreCase(name);
        if (category.isPresent()) {
            log.debug("Category already exists with name: {}", name);
            throw new RuntimeException(String.format("Category already exists with name: %s", name));
        }
    }

    @Override
    public List<CategoryResponse> getAll() {
        return this.categoryRepository.findAll()
        .stream()
        .map(this.categoryMapper::toResponse)
        .collect(Collectors.toList());
    }
}