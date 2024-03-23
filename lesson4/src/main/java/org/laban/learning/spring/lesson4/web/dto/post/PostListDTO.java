package org.laban.learning.spring.lesson4.web.dto.post;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PostListDTO {
    private List<PostListItemDTO> posts;
    private Integer page;
    private Integer pageSize;
    private Integer pageCount;
    private List<Long> categories;
    private List<Long> authors;
}
