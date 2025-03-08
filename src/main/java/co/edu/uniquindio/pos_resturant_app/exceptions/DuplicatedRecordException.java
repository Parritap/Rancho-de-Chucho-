package co.edu.uniquindio.pos_resturant_app.exceptions;

public class DuplicatedRecordException extends RuntimeException {
    public DuplicatedRecordException(String message) {
        super(message);
    }
}
