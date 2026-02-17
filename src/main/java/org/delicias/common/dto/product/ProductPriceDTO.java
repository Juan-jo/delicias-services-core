package org.delicias.common.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductPriceDTO(
        Integer productTmplId,
        String name,
        String description,
        String pictureUrl,
        BigDecimal listPrice,
        List<AttributeDTO> attributes
        ) {

    @Builder
    public record AttributeDTO(
            Integer attrId,
            String name,
            List<AttributeValueDTO> values,
            Short sequence
    ) {}

    @Builder
    public record AttributeValueDTO(
            Integer attrValueId,
            String name,
            BigDecimal extraPrice,
            Short sequence
    ) {}
}
