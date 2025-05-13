package co.edu.uniquindio.pos_resturant_app.exceptions;

import co.edu.uniquindio.pos_resturant_app.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.hc.core5.annotation.Experimental;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);




    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            RecordNotFoundException ex, HttpServletRequest request) {

        ErrorResponse error = ErrorResponse.builder().
                errorCode(ERROR_CODE.ENTITY_NOT_FOUND.value()).
                message(ex.getMessage()).
                timestamp(LocalDateTime.now()).
                build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RecordNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleRecordNotFoundException(
            RecordNotFoundException ex, HttpServletRequest request) {

        ErrorResponse error = ErrorResponse.builder().
                errorCode(ERROR_CODE.ENTITY_NOT_FOUND.value()).
                message(ex.getMessage()).
                timestamp(LocalDateTime.now()).
                build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatedRecordException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedRecordException(
            DuplicatedRecordException ex) {

        ErrorResponse error = ErrorResponse.builder().
                errorCode(ERROR_CODE.DUPLICATE_RECORD.value()).
                message(ex.getMessage()).
                timestamp(LocalDateTime.now()).
                build();


        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * Captures MethodArgumentNotValidException exceptions that occur when request
     * data fails validation (like when fields in request bodies don't meet
     * requirements defined by annotations like @NotNull, @Size, etc.)
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Failed");
        response.put("errors", errors);
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(
            Exception ex, HttpServletRequest request) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error, Exception",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
