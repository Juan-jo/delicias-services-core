package org.delicias.minio.utils;

import lombok.Getter;

@Getter
public enum MinioRS {
    FILL("fill"),
    FIT("fit");

    private final String value;

    MinioRS(String value) {
        this.value = value;
    }

}
/*

* Ejemplo Práctico
Si tienes una imagen de 800x600 (proporción 4:3):

fill:320:120: Recortará la imagen a 320x120, perdiendo partes superior e inferior
fit:320:120: Escalará la imagen a 160x120 (manteniendo 4:3), dejando espacio vacío a los lados si el contenedor es 320x120

* */
