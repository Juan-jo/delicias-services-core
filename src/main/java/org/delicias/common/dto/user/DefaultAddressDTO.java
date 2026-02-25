package org.delicias.common.dto.user;

import lombok.Builder;

@Builder
public record DefaultAddressDTO(
        boolean exists,
        double latitude,
        double longitude,
        Data data
) {

    @Builder
    public record Data(
            Integer id,
            String name,
            String address,
            String addressType
    ) {
    }
}
