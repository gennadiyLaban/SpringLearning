package org.laban.learning.spring.lessonfinal.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Embeddable
public class Address {
    @NotBlank(groups = ValidationGroup.Create.class)
    private String city;

    @NotBlank(groups = ValidationGroup.Create.class)
    private String street;

    @NotBlank(groups = ValidationGroup.Create.class)
    private String number;
}
