package org.delicias.common.dto.restaurant;

import lombok.Getter;

@Getter
public enum StoreType {
    RESTAURANT("Restaurante"),
    SUPER("Super"),
    PHARMACY("Farmacia"),
    BEER("Cerveza y licores"),
    STORE("Tienda");

    private final String description;

    StoreType(String description) {
        this.description = description;
    }

}
