package com.cardconnect.stom.stockexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {
    @JsonIgnore
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "price")
    private double price;
}