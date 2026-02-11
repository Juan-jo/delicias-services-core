package org.delicias.common.dto.product;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResumeDTO(
        Integer id,
        String name,
        String description,
        String pictureUrl,
        BigDecimal listPrice
) { }
