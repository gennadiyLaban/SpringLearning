package org.laban.learning.spring.lessonfinal.web.dto.booking;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.laban.learning.spring.lessonfinal.web.validation.custom.booking.BookingStartBeforeEnd;
import org.laban.learning.spring.lessonfinal.web.validation.custom.booking.BookingValidationGroup;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@BookingStartBeforeEnd(groups = BookingValidationGroup.class)
public class BookingDTO {
    @Null(groups = ValidationGroup.Create.class)
    private Long id;

    @NotNull(groups = ValidationGroup.Create.class)
    private Instant startDate;

    @NotNull(groups = ValidationGroup.Create.class)
    private Instant endDate;

    @NotNull(groups = ValidationGroup.Create.class)
    private Long roomId;

    @NotNull(groups = ValidationGroup.Create.class)
    private Long userId;
}
