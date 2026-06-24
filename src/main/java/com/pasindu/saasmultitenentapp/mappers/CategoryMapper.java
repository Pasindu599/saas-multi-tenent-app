package com.pasindu.saasmultitenentapp.mappers;

import org.springframework.stereotype.Service;

import com.pasindu.saasmultitenentapp.entities.Category;
import com.pasindu.saasmultitenentapp.requests.CategoryRequest;
import com.pasindu.saasmultitenentapp.responses.CategoryResponse;

@Service
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {
        return Category.builder()
            .name(request.getName())
            .description(request.getDescription())
            .deleted(false)
            .build();
    }

    public CategoryResponse toResponse(Category entity) {
        return CategoryResponse.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .build();
    }

}
