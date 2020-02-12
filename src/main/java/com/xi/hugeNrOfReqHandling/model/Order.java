package com.xi.hugeNrOfReqHandling.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * @author xichen created on 10/02/2020
 */

public class Order {
    private final UUID id;

    @NotBlank
    private final String name;

    public Order(@JsonProperty("id") UUID id,
                 @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
