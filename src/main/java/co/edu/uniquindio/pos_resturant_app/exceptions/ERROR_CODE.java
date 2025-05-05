package co.edu.uniquindio.pos_resturant_app.exceptions;

public enum ERROR_CODE {
    ENTITY_NOT_FOUND("ENTITY_NOT_FOUND"),
    ENTITY_ALREADY_EXISTS("ENTITY_ALREADY_EXISTS"),
    VALIDATION_ERROR("VALIDATION_ERROR"),
    DATA_INTEGRITY_VIOLATION("DATA_INTEGRITY_VIOLATION"), // Essentially, it's a signal that you're trying to do something that would corrupt the accuracy, consistency, or validity of your data.
    UNEXPECTED_ERROR("UNEXPECTED_ERROR"),
    INVALID_ENTITY_STATE("INVALID_ENTITY_STATE")// Attempting an operation on an entity that is in an invalid state according to business rules (e.g., trying to ship an order that hasn't been paid).
    ;
    private final String code;

    ERROR_CODE(String code) {
        this.code = code;
    }

    public String value() {
        return code;
    }
}
