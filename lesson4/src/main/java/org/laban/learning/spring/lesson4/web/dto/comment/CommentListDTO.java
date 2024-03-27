package org.laban.learning.spring.lesson4.web.dto.comment;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommentListDTO {
    private List<CommentDTO> comments;
    private Long postId;
    private Integer page;
    private Integer pageSize;
    private Integer pageCount;

}
