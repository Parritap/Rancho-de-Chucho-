package co.edu.uniquindio.pos_resturant_app.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String entity) {
        super("Entity not found -->" + entity);
    }

}
