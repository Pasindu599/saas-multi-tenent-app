package com.pasindu.saasmultitenentapp.services;

import com.pasindu.saasmultitenentapp.responses.CategoryResponse;
import java.util.List;

public interface BasicService<I, O> {

    void create(final I request);
    void update(final String id, final I request);
    CategoryResponse getByID(final String id);
    void delete(final String id);
    List<O> getAll();
}