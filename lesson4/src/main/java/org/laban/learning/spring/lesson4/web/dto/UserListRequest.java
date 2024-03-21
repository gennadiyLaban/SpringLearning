package org.laban.learning.spring.lesson4.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserListRequest {
    @NotNull
    @PositiveOrZero
    @Builder.Default
    private Integer page = 0;

    @NotNull
    @Positive
    @Builder.Default
    private Integer size = Integer.MAX_VALUE;
}
