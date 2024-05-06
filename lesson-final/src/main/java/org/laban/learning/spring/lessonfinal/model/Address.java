package org.laban.learning.spring.lessonfinal.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.laban.learning.spring.lessonfinal.web.validation.custom.NullOrNotBlank;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;

@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Embeddable
public class Address {
    @NotBlank(groups = ValidationGroup.Create.class)
    @NullOrNotBlank(groups = ValidationGroup.Update.class)
    private String city;

    @NotBlank(groups = ValidationGroup.Create.class)
    @NullOrNotBlank(groups = ValidationGroup.Update.class)
    private String street;

    @NotBlank(groups = ValidationGroup.Create.class)
    @NullOrNotBlank(groups = ValidationGroup.Update.class)
    private String number;
}
