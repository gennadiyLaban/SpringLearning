package org.laban.learning.spring.lesson4.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.exception.CategoryNotFoundException;
import org.laban.learning.spring.lesson4.mapper.CategoryMapper;
import org.laban.learning.spring.lesson4.model.Category;
import org.laban.learning.spring.lesson4.repository.CategoryRepository;
import org.laban.learning.spring.lesson4.utils.BeanUtils;
import org.laban.learning.spring.lesson4.web.dto.category.CategoryDTO;
import org.laban.learning.spring.lesson4.web.dto.category.CategoryLIstDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public CategoryDTO findCategoryDTOById(@Nonnull Long id) {
        return findCategoryById(id)
                .map(categoryMapper::categoryToCategoryDTO)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Optional<Category> findCategoryById(@Nonnull Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public CategoryLIstDTO findAllByDTO(@Nonnull Pageable pageable) {
        var page = findAll(pageable);
        return categoryMapper.categoryPageToCategoryListDTO(page);
    }

    @Transactional(readOnly = true)
    public Page<Category> findAll(@Nonnull Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Transactional
    public CategoryDTO createCategoryByDTO(@Nonnull CategoryDTO categoryDTO) {
        var upsertCategory = categoryMapper.categoryDTOtoCategory(categoryDTO);
        var createdCategory = categoryRepository.save(upsertCategory);
        return categoryMapper.categoryToCategoryDTO(createdCategory);
    }

    @Transactional
    public void updateCategoryByDTO(@Nonnull CategoryDTO categoryDTO) {
        var upsertCategory = categoryMapper.categoryDTOtoCategory(categoryDTO);
        var existedCategory = categoryRepository.findById(upsertCategory.getId())
                .orElseThrow(() -> new CategoryNotFoundException(upsertCategory.getId()));
        BeanUtils.copyNonNullProperties(upsertCategory, existedCategory);
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new CategoryNotFoundException(id);
        }
    }
}
