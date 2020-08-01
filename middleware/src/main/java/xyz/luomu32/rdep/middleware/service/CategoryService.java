package xyz.luomu32.rdep.middleware.service;

import xyz.luomu32.rdep.middleware.pojo.CategoryRequest;
import xyz.luomu32.rdep.middleware.pojo.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> fetch();

    void create(CategoryRequest categoryRequest);

    void updateName(Long id, String name);

    void delete(Long id);
}
