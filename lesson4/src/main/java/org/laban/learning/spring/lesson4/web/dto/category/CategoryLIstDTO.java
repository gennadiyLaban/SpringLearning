package org.laban.learning.spring.lesson4.web.dto.category;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class CategoryLIstDTO {
    @Singular
    private List<CategoryDTO> categories;
    private Integer page;
    private Integer pageSize;
    private Integer pageCount;
}
