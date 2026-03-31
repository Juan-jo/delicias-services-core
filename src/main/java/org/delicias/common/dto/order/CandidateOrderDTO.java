package org.delicias.common.dto.order;

import lombok.Builder;
import org.delicias.common.adjusment.OrderAdjustment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Builder
public record CandidateOrderDTO(
        Integer restaurantTmplId,
        Integer deliveryAddressId,
        List<Line> lines,
        List<OrderAdjustment> adjustments,
        BigDecimal subtotal,
        BigDecimal total,
        Map<String, Object> metadata
) {

    @Builder
    public record Line(
            Integer productId,
            Short qty,
            String name,
            BigDecimal priceUnit,
            BigDecimal priceTotal,
            List<String> attributes,
            String pictureUrl
    ) {}

}
