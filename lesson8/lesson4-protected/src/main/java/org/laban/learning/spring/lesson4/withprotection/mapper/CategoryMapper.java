package org.laban.learning.spring.lesson4.withprotection.mapper;

import jakarta.annotation.Nullable;
import org.laban.learning.spring.lesson4.withprotection.model.Category;
import org.laban.learning.spring.lesson4.withprotection.web.dto.category.CategoryDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.category.CategoryLIstDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@DecoratedWith(CategoryMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(Category category);

    Category categoryDTOtoCategory(CategoryDTO categoryDTO);

    Category categoryIdToCategory(@Nullable Long categoryId);

    List<Category> categoryIdsToCategoryList(@Nullable List<Long> ids);


    default CategoryLIstDTO categoryPageToCategoryListDTO(Page<Category> page) {
        return CategoryLIstDTO.builder()
                .categories(categoryListToCategoryDTOlist(page.getContent()))
                .page(page.getNumber())
                .pageSize(page.getSize())
                .pageCount(page.getTotalPages())
                .build();
    }

    default List<CategoryDTO> categoryListToCategoryDTOlist(List<Category> categories) {
        return categories.stream()
                .map(this::categoryToCategoryDTO)
                .toList();
    }
}
