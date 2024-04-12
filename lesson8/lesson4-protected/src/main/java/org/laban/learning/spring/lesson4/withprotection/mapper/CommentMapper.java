package org.laban.learning.spring.lesson4.withprotection.mapper;

import org.laban.learning.spring.lesson4.withprotection.model.Comment;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentListDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentRequestDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@DecoratedWith(CommentMapperDelegate.class)
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

    @Mapping(target = "comments", source = "page.content")
    @Mapping(target = "page", source = "page.number")
    @Mapping(target = "pageSize", source = "page.size")
    @Mapping(target = "pageCount", source = "page.totalPages")
    @Mapping(target = "postId", source = "postId")
    CommentListDTO commentPageToCommentListDTO(Long postId, Page<Comment> page);

    Comment commentRequestDTOtoComment(CommentRequestDTO request);
}
