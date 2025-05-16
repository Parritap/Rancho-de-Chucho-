package co.edu.uniquindio.pos_resturant_app.response;


import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class ResponseSet {
    @Min(0) private int page;
    @Min(1) private int size;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
}
