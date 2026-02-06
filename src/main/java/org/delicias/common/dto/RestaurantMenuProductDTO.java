package org.delicias.common.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RestaurantMenuProductDTO(
        Integer id,
        String name,
        String description,
        String pictureUrl,
        BigDecimal listPrice
) { }
