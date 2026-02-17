package org.delicias.common.adjusment;

import lombok.Getter;

@Getter
public enum AdjustmentType {

    CHARGE(""),
    PROMOTION("Promoción");

    private final String description;

    AdjustmentType(String description) {
        this.description = description;
    }

}