package org.delicias.common.dto.user;

public record UserShoppingAddressDTO(
        Integer id,
        String name,
        String address,
        String addressType
) { }
