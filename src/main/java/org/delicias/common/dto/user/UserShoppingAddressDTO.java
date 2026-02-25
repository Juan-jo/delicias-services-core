package org.delicias.common.dto.user;

public record UserShoppingAddressDTO(
        String name,
        String address,
        String addressType
) { }
