package org.laban.learning.spring.lesson4.withprotection.web.dto.post;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Builder
@Data
public class PostListRequest {
    @NotNull
    @PositiveOrZero
    private Integer page;

    @Nonnull
    @Positive
    private Integer size;

    @Builder.Default
    private List<Long> categories = Collections.emptyList();

    @Builder.Default
    private List<Long> authors = Collections.emptyList();
}
