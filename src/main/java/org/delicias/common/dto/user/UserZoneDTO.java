package org.delicias.common.dto.user;

import java.util.UUID;

public record UserZoneDTO(
        UUID userId,
        Integer zoneId
) {}
