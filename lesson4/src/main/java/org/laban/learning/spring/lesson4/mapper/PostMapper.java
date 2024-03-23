package org.laban.learning.spring.lesson4.mapper;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.laban.learning.spring.lesson4.model.Post;
import org.laban.learning.spring.lesson4.web.dto.post.PostDTO;
import org.laban.learning.spring.lesson4.web.dto.post.PostListDTO;
import org.laban.learning.spring.lesson4.web.dto.post.PostListItemDTO;
import org.laban.learning.spring.lesson4.web.dto.post.PostRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CommentMapper.class, UserMapper.class, CategoryMapper.class}
)
public interface PostMapper {
    PostDTO postToPostDTO(Post post);

    @Mapping(target = "commentsCount", expression = "java(post.getComments().size())")
    PostListItemDTO postToPostListItemDTO(Post post);

    @Mapping(target = "posts", source = "page.content")
    @Mapping(target = "page", source = "page.number")
    @Mapping(target = "pageSize", source = "page.size")
    @Mapping(target = "pageCount", source = "page.totalPages")
    @Mapping(target = "categories", source = "categories")
    @Mapping(target = "authors", source = "authors")
    PostListDTO postPageToPostListDTO(
            @Nonnull Page<Post> page,
            @Nullable List<Long> categories,
            @Nullable List<Long> authors
    );

    default PostListDTO postPageToPostListDTO(@Nonnull Page<Post> page) {
        return postPageToPostListDTO(page, null, null);
    }

    default List<PostListItemDTO> postListToPostListItemDTOlist(List<Post> posts) {
        return posts.stream()
                .map(this::postToPostListItemDTO)
                .toList();
    }

    @Mapping(target = "user", source = "userId")
    Post postRequestDTOtoPost(PostRequestDTO request);
}
