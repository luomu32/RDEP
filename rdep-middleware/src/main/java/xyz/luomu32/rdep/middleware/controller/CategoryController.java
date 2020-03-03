package xyz.luomu32.rdep.middleware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.middleware.pojo.CategoryRequest;
import xyz.luomu32.rdep.middleware.pojo.CategoryResponse;
import xyz.luomu32.rdep.middleware.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("middleware/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> fetch() {
        return categoryService.fetch();
    }

    @PostMapping
    public void create(CategoryRequest categoryRequest) {
        categoryService.create(categoryRequest);
    }

    @PutMapping("{id}")
    public void update(@PathVariable Long id,
                       @RequestParam String name) {
        categoryService.updateName(id, name);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {

    }
}
