package org.delicias.common.dto.restaurant;

import lombok.Builder;

@Builder
public record RestaurantResumeDTO(
        Integer id,
        String name,
        String description,
        String logoUrl
) { }
