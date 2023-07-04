package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.dao.CategoryRepository;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;

import java.util.List;

import static ru.practicum.Constant.ALREADY_EXIST_CATEGORY;
import static ru.practicum.Constant.NOT_FOUND_CATEGORY;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.toCategory(categoryDto);
        if (categoryRepository.existsAllByName(category.getName())) {
            throw new ValidationException(String.format(ALREADY_EXIST_CATEGORY, category.getName()));
        }
        Category saved = categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(saved);
    }

    @Override
    public CategoryDto updateCategory(long catId, CategoryDto categoryDto) {
        if (categoryRepository.existsAllByName(categoryDto.getName())) {
            Category catStorage = categoryRepository.findAllByName(categoryDto.getName()).get(0);
            if (catStorage.getId() != catId) {
                throw new ValidationException(String.format(ALREADY_EXIST_CATEGORY, categoryDto.getName()));
            }
        }
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_CATEGORY, catId)));
        category.setName(categoryDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(long catId) {
        if (!categoryRepository.existsById(catId)) {
            throw new NotFoundException(String.format(NOT_FOUND_CATEGORY, catId));
        }
        if (eventRepository.existsAllByCategoryId(catId)) {
            throw new ValidationException("The category is not empty.");
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public List<CategoryDto> getAllCategories(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        Page<Category> allCategories = categoryRepository.findAll(pageRequest);
        return CategoryMapper.listToCategoryDto(allCategories);
    }

    @Override
    public CategoryDto getCategoryByID(long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_CATEGORY, catId)));
        return CategoryMapper.toCategoryDto(category);
    }

}
