package org.laban.learning.spring.lesson4.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.exception.PostNotFoundException;
import org.laban.learning.spring.lesson4.mapper.PostMapper;
import org.laban.learning.spring.lesson4.model.Post;
import org.laban.learning.spring.lesson4.repository.PostRepository;
import org.laban.learning.spring.lesson4.security.CheckAuthorization;
import org.laban.learning.spring.lesson4.utils.BeanUtils;
import org.laban.learning.spring.lesson4.utils.SpecificationUtils;
import org.laban.learning.spring.lesson4.web.dto.post.PostDTO;
import org.laban.learning.spring.lesson4.web.dto.post.PostListDTO;
import org.laban.learning.spring.lesson4.web.dto.post.PostListRequest;
import org.laban.learning.spring.lesson4.web.dto.post.PostRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public PostDTO findPostDTObyId(@Nonnull Long id) {
        return postMapper.postToPostDTO(getPostById(id));
    }

    @Transactional(readOnly = true)
    public Post getPostById(@Nonnull Long id) {
        return findPostById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Optional<Post> findPostById(@Nonnull Long id) {
        return postRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public PostListDTO findAllByDTO(@Nonnull PostListRequest request) {
        return postMapper.postPageToPostListDTO(
                findAll(
                        Pageable.ofSize(request.getSize()).withPage(request.getPage()),
                        SpecificationUtils.specificationOf(request)
                ),
                request.getCategories(),
                request.getAuthors()
        );
    }

    @Transactional(readOnly = true)
    public PostListDTO findAllByDTO(@Nonnull Pageable pageable) {
        return postMapper.postPageToPostListDTO(findAll(pageable));
    }

    @Transactional(readOnly = true)
    public Page<Post> findAll(@Nonnull Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Post> findAll(@Nonnull Pageable pageable, @Nonnull Specification<Post> specification) {
        return postRepository.findAll(specification, pageable);
    }

    @Transactional
    public Long createPostByDTO(@Nonnull PostRequestDTO request) {
        var upsertPost = postMapper.postRequestDTOtoPost(request);
        var createdPost = postRepository.save(upsertPost);
        return createdPost.getId();
    }

    @CheckAuthorization(paramName = "request", checkedEntity = Post.class)
    @Transactional
    public void updatePostByDTO(@Nonnull PostRequestDTO request) {
        var upsertPost = postMapper.postRequestDTOtoPost(request);
        var existedPost = getPostById(upsertPost.getId());
        BeanUtils.copyNonNullProperties(upsertPost, existedPost);
    }

    @CheckAuthorization(paramName = "id", checkedEntity = Post.class)
    @Transactional
    public void deletePostById(@Nonnull Long id) {
        postRepository.deleteById(id);
    }
}
