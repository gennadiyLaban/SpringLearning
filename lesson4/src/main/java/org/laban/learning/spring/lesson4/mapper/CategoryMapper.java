package org.laban.learning.spring.lesson4.mapper;

import org.laban.learning.spring.lesson4.model.Category;
import org.laban.learning.spring.lesson4.web.dto.category.CategoryDTO;
import org.laban.learning.spring.lesson4.web.dto.category.CategoryLIstDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(Category category);

    Category categoryDTOtoCategory(CategoryDTO categoryDTO);


    default CategoryLIstDTO categoryPageToCategoryListDTO(Page<Category> page) {
        return CategoryLIstDTO.builder()
                .categories(page.getContent().stream()
                        .map(this::categoryToCategoryDTO)
                        .toList())
                .page(page.getNumber())
                .pageSize(page.getSize())
                .pageCount(page.getTotalPages())
                .build();
    }
}
