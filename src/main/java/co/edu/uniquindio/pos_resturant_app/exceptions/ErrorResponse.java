package co.edu.uniquindio.pos_resturant_app.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private String errorCode;
    private String message;
    private LocalDateTime timestamp;
    private List<String> details = new ArrayList<>();



    public void addDetail(String detail) {
        details.add(detail);
    }
}
