package com.pasindu.saasmultitenentapp.services;

import com.pasindu.saasmultitenentapp.responses.CategoryResponse;

public interface BasicService<I, O> {

    void create(final I request);
    void update(final String id, final I request);
    CategoryResponse getByID(final String id);
    void delete(final String id);
}