package org.laban.learning.spring.lessonfinal.web.dto.hotel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.laban.learning.spring.lessonfinal.configuration.AppContestants;
import org.laban.learning.spring.lessonfinal.web.validation.custom.NullOrNotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Jacksonized
public class HotelListRequestDTO {
    @Valid
    @NotNull
    @Builder.Default
    private Page page = new Page(0, AppContestants.DEFAULT_PAGE_SIZE);

    @Valid
    @NotNull
    @Builder.Default
    private Filter filter = new Filter();

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Page {
        @PositiveOrZero
        private Integer number;

        @Positive
        private Integer size;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Filter {
        @Positive
        private Long hotelId;

        @NullOrNotBlank
        private String name;

        @NullOrNotBlank
        private String title;

        @NullOrNotBlank
        private String city;

        @NullOrNotBlank
        private String street;

        @NullOrNotBlank
        private String streetNumber;

        @Positive
        private Long maxDistanceFromCenter;

        @PositiveOrZero
        private Integer minRating;

        @PositiveOrZero
        private Integer minNumberOfRating;
    }
}
