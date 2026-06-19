package org.delicias.minio.utils;

import lombok.Getter;

@Getter
public enum MinioSize {
    SMALL(80, 80, "sm"),
    MEDIUM(150, 150, "sm"),
    BANNER_CE(320, 120, "ce"),
    BANNER_SM(320, 120, "sm");

    private final int width;
    private final int height;
    private final String gravity;

    MinioSize(int width, int height, String gravity) {
        this.width = width;
        this.height = height;
        this.gravity = gravity;
    }

}