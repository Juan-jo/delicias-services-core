package org.delicias.common.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.panache.common.Sort;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.delicias.common.validation.OnFilter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseFilterDTO {

    @NotNull(message = "The page number parameter is mandatory.", groups = { OnFilter.class })
    private Integer page;

    @NotNull(message = "The elements number parameter is mandatory.", groups = { OnFilter.class })
    private Integer size;

    @NotNull(message = "The column name to sort parameter is mandatory.", groups = { OnFilter.class })
    private String orderColumn;

    @NotNull(message = "The order dir parameter is mandatory.", groups = { OnFilter.class })
    private String orderDir;


    public Sort.Direction toOrderDirection() {

        if (orderDir == null) {
            return Sort.Direction.Ascending;
        }

        return switch (orderDir.toLowerCase()) {
            case "desc", "descending" -> Sort.Direction.Descending;
            default -> Sort.Direction.Ascending;
        };
    }

}
