package org.laban.learning.spring.lessonfinal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Entity(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "address_city", nullable = false)),
            @AttributeOverride(name = "street", column = @Column(name = "address_street", nullable = false)),
            @AttributeOverride(name = "number", column = @Column(name = "address_number", nullable = false))
    })
    private Address address;

    @Column(nullable = false)
    private Long distanceFromCenter;

    @Column(nullable = false)
    private BigDecimal rating;

    @Column(nullable = false)
    private Integer numberOfRating;
}
