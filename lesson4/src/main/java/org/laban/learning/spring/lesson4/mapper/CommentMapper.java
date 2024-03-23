package org.laban.learning.spring.lesson4.mapper;

import org.laban.learning.spring.lesson4.model.Comment;
import org.laban.learning.spring.lesson4.web.dto.comment.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class}
)
public interface CommentMapper {
    @Mapping(target = "postId", source = "post.id")
    CommentDTO commentToCommentDTO(Comment comment);

    default List<CommentDTO> commentListToCommentDTOlist(List<Comment> comments) {
        return comments.stream()
                .map(this::commentToCommentDTO)
                .toList();
    }
}
