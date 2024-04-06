package org.laban.learning.spring.lesson7.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TaskStatus {
    @JsonProperty("todo") TODO,
    @JsonProperty("inProgress") IN_PROGRESS,
    @JsonProperty("done") DONE
}
