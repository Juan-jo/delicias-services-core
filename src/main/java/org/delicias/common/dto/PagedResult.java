package org.delicias.common.dto;


import java.util.List;

public record PagedResult<T>(
        List<T> data,
        long total,
        int page,
        int size
) {}

