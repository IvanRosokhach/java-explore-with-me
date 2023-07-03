package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto saveCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(long categoryId, CategoryDto categoryDto);

    void deleteCategory(long categoryId);

    List<CategoryDto> getAllCategories(int from, int size);

    CategoryDto getCategoryByID(long categoryId);

}
