package org.laban.learning.spring.lessonfinal.web.dto.room;

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
import org.laban.learning.spring.lessonfinal.web.dto.Page;
import org.laban.learning.spring.lessonfinal.web.validation.custom.BookingStartBeforeEnd;
import org.laban.learning.spring.lessonfinal.web.validation.custom.NullOrNotBlank;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Jacksonized
public class HotelRoomListRequestDTO {
    @Valid
    @NotNull
    @Builder.Default
    private Page page = new Page(0, AppContestants.DEFAULT_PAGE_SIZE);

    @Valid
    @NotNull
    @Builder.Default
    private Filter filter = Filter.builder().build();

    @Builder
    @Jacksonized
    @BookingStartBeforeEnd
    public record BookingDates(
            @NotNull Instant startDate,
            @NotNull Instant endDate
    ) {
    }

    @Builder
    @Jacksonized
    public record Filter(
            Long roomId,
            @NullOrNotBlank String name,
            @PositiveOrZero BigDecimal minPrice,
            @PositiveOrZero BigDecimal maxPrice,
            @Positive Integer targetCapacity,
            @Valid BookingDates bookingDates,
            Long hotelId
    ) {
    }
}
