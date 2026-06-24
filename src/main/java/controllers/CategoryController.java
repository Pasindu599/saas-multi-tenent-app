package controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.pasindu.saasmultitenentapp.services.CategoryService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pasindu.saasmultitenentapp.requests.CategoryRequest;
import com.pasindu.saasmultitenentapp.responses.CategoryResponse;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(
        @Valid
        @RequestBody
        final CategoryRequest request
    ) {
        categoryService.create(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{category-id}")
    public ResponseEntity<Void> updateCategory(
        @Valid
        @RequestBody
        final CategoryRequest request,
        @PathVariable("category-id")
        final String categoryId
    ) {
        this.categoryService.update(categoryId, request);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{category-id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
        @PathVariable("category-id")
        final String categoryId
    ) {
        return ResponseEntity.ok(this.categoryService.getByID(categoryId));
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<Void> deleteCategory(
        @PathVariable("category-id")
        final String categoryId
    ) {
        this.categoryService.delete(categoryId);
        return ResponseEntity.noContent().build();
    }
}
