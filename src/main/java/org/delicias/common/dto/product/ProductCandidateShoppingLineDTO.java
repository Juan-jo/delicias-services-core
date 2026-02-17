package org.delicias.common.dto.product;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Set;

@Builder
public record ProductCandidateShoppingLineDTO(
        Integer productTmplId,
        BigDecimal listPrice,
        Integer restaurantTmplId,
        Set<AttributeValueDTO> attrValues
)
{
    @Builder
    public record AttributeValueDTO(
            Integer attrValueId,
            BigDecimal extraPrice
    ) {}
}
