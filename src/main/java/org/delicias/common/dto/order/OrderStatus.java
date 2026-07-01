package org.delicias.common.dto.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED(
            "📝 Pedido creado",
            "Hemos recibido tu pedido correctamente."
    ),
    PENDING_PAYMENT(
            "💳 Pago en proceso",
            "Estamos procesando tu pago. Esto puede tomar unos segundos."
    ),
    ORDERED(
            "✅ Pedido confirmado",
            "Tu pedido ha sido confirmado y enviado al negocio."
    ),
    ACCEPTED(
            "👨‍🍳 Pedido aceptado",
            "El negocio aceptó tu pedido y comenzará a prepararlo."
    ),
    COOKING(
            "🍳 Preparando tu pedido",
            "Tu pedido se está preparando con mucho cuidado."
    ),
    READY_FOR_DELIVERY(
            "🔎 Buscando repartidor",
            "Estamos asignando un repartidor para entregar tu pedido."
    ),
    DELIVERY_ASSIGNED_ORDER(
            "🧑 Repartidor asignado",
            "¡Buenas noticias! Un repartidor ha sido asignado a tu pedido."
    ),
    DELIVERY_ROAD_TO_STORE(
            "📍 Camino al negocio",
            "El repartidor se dirige al negocio para recoger tu pedido."
    ),
    DELIVERY_ROAD_TO_DESTINATION(
            "🛵 En camino",
            "Tu pedido va en camino hacia tu ubicación."
    ),
    READY_FOR_PICKUP(
            "📦 Listo para recoger",
            "Tu pedido está listo para que pases por él."
    ),
    DELIVERED(
            "✅ Pedido entregado",
            "¡Disfruta tu pedido! Tu pedido fue entregado correctamente."
    ),
    CANCELLED(
            "❌ Pedido cancelado",
            "Tu pedido ha sido cancelado. Si tienes dudas, contáctanos."
    ),
    REJECTED(
            "🚫 Pedido rechazado",
            "El negocio no pudo aceptar tu pedido."
    );

    private final String title;
    private final String description;

    OrderStatus(String title, String description) {
        this.title = title;
        this.description = description;
    }

}