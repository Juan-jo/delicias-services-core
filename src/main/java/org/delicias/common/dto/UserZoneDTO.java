package org.delicias.common.dto;

import java.util.UUID;

public record UserZoneDTO(
        UUID userId,
        Integer zoneId
) {}
