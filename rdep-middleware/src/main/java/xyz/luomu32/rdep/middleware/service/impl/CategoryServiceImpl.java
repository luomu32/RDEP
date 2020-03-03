package xyz.luomu32.rdep.middleware.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.middleware.dao.CategoryDao;
import xyz.luomu32.rdep.middleware.entity.Category;
import xyz.luomu32.rdep.middleware.pojo.CategoryRequest;
import xyz.luomu32.rdep.middleware.pojo.CategoryResponse;
import xyz.luomu32.rdep.middleware.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<CategoryResponse> fetch() {
        return categoryDao.findAll().stream().map(c -> {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(c.getId());
            categoryResponse.setName(c.getName());
            return categoryResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public void create(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setId(categoryRequest.getId());
        category.setName(categoryRequest.getName());
        categoryDao.save(category);
    }

    @Override
    public void updateName(Long id, String name) {
        Category category = categoryDao.findById(id)
                .orElseThrow(() -> new RuntimeException("category.not.found"));

        category.setName(name);
        categoryDao.save(category);
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
