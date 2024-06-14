package org.laban.learning.spring.lessonfinal.utils;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.Path;
import org.laban.learning.spring.lessonfinal.model.*;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelListRequestDTO;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomListRequestDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public class SpecificationUtils {
    private SpecificationUtils() {
        throw new UnsupportedOperationException("This is utils class!");
    }

    public static Specification<Booking> bookingsOfHotel(@Nonnull Long hotelId) {
        return Specification.where(((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get(Booking.Fields.room)
                        .get(HotelRoom.Fields.hotel)
                        .get(Hotel.Fields.id),
                hotelId
        )));
    }

    public static Specification<Booking> bookingsOfUser(@Nonnull Long userId) {
        return Specification.where(((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get(Booking.Fields.user)
                        .get(User.Fields.id),
                userId
        )));
    }

    @Nonnull
    public static Specification<Hotel> hotelsByFilter(@Nonnull HotelListRequestDTO.Filter filter) {
        Specification<Hotel> idSpec = equalNotNull(path -> path.get(Hotel.Fields.id), filter.getHotelId());
        Specification<Hotel> nameSpec = likeNotNull(path -> path.get(Hotel.Fields.name), filter.getName());
        Specification<Hotel> titleSpec = likeNotNull(path -> path.get(Hotel.Fields.title), filter.getTitle());
        Specification<Hotel> citySpec = likeNotNull(
                path -> path.get(Hotel.Fields.address).get(Address.Fields.city),
                filter.getCity());
        Specification<Hotel> streetSpec = likeNotNull(
                path -> path.get(Hotel.Fields.address).get(Address.Fields.street),
                filter.getStreet());
        Specification<Hotel> streetNumberSpec = likeNotNull(
                path -> path.get(Hotel.Fields.address).get(Address.Fields.number),
                filter.getStreetNumber());
        Specification<Hotel> maxDistanceSpec = lessThanOrEqualNotNull(
                path -> path.get(Hotel.Fields.distanceFromCenter),
                filter.getMaxDistanceFromCenter());
        Specification<Hotel> minRatingSpec = greaterThanOrEqualNotNull(
                path -> path.get(Hotel.Fields.rating),
                filter.getMinRating());
        Specification<Hotel> minNumberOfRating = greaterThanOrEqualNotNull(
                path -> path.get(Hotel.Fields.numberOfRating),
                filter.getMinNumberOfRating());

        return Specification.allOf(
                idSpec,
                nameSpec,
                titleSpec,
                citySpec,
                streetSpec,
                streetNumberSpec,
                maxDistanceSpec,
                minRatingSpec,
                minNumberOfRating
        );
    }

    public static Specification<HotelRoom> hotelRoomsByFilter(@Nonnull HotelRoomListRequestDTO.Filter filter) {
        Specification<HotelRoom> roomIdSpec = equalNotNull(path -> path.get(HotelRoom.Fields.id), filter.roomId());
        Specification<HotelRoom> nameSpec = likeNotNull(path -> path.get(HotelRoom.Fields.name), filter.name());
        Specification<HotelRoom> minPriceSpec = greaterThanOrEqualNotNull(
                path -> path.get(HotelRoom.Fields.price),
                filter.minPrice());
        Specification<HotelRoom> maxPriceSpec = lessThanOrEqualNotNull(
                path -> path.get(HotelRoom.Fields.price),
                filter.maxPrice());
        Specification<HotelRoom> capacitySpec = greaterThanOrEqualNotNull(
                path -> path.get(HotelRoom.Fields.maxCapacity),
                filter.targetCapacity());
        Specification<HotelRoom> hotelIdSpec = equalNotNull(
                path -> path.get(HotelRoom.Fields.hotel).get(Hotel.Fields.id),
                filter.hotelId());

        Specification<HotelRoom> bookingRangeSpec;
        if (filter.bookingDates() != null) {
            bookingRangeSpec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.isFalse(
                    criteriaBuilder.function("isBooked", Boolean.class,
                            root.get(HotelRoom.Fields.id),
                            criteriaBuilder.literal(filter.bookingDates().startDate()),
                            criteriaBuilder.literal(filter.bookingDates().endDate())
                    )));
        } else {
            bookingRangeSpec = Specification.where(null);
        }

        return Specification.allOf(
                roomIdSpec,
                nameSpec,
                minPriceSpec,
                maxPriceSpec,
                capacitySpec,
                hotelIdSpec,
                bookingRangeSpec);
    }

    private static <T> Specification<T> equalNotNull(
            @Nonnull Function<Path<T>, Path<?>> pathMapper,
            @Nullable Object value
    ) {
        if (value == null) {
            return Specification.where(null);
        }

        return Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(pathMapper.apply(root), value));
    }

    private static <T> Specification<T> likeNotNull(
            @Nonnull Function<Path<T>, Path<String>> pathMapper,
            @Nullable String value
    ) {
        if (value == null) {
            return Specification.where(null);
        }

        return (root, query, criteriaBuilder) -> {
            var path = pathMapper.apply(root);
            return criteriaBuilder.like(
                    criteriaBuilder.lower(path),
                    "%" + value.toLowerCase() + "%"
            );
        };
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <T> Specification<T> lessThanOrEqualNotNull(
            @Nonnull Function<Path<T>, Path<Comparable>> pathMapper,
            @Nullable Comparable value
    ) {
        if (value == null) {
            return Specification.where(null);
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(
                pathMapper.apply(root),
                value
        );
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <T> Specification<T> greaterThanOrEqualNotNull(
            @Nonnull Function<Path<T>, Path<Comparable>> pathMapper,
            @Nullable Comparable value
    ) {
        if (value == null) {
            return Specification.where(null);
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(
                pathMapper.apply(root),
                value
        );
    }


}
