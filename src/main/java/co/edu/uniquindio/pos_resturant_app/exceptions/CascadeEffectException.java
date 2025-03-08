package co.edu.uniquindio.pos_resturant_app.exceptions;

/**
 * Esta excepción es lanzada en caso de la eliminación de una fila en la base de datos
 * afecte a filas en otras tablas.
 * Exception thrown when a cascade effect occurs in the database.
 * This typically happens when trying to delete an entity that is referenced by another entity.
 */
public class CascadeEffectException extends RuntimeException {
    public CascadeEffectException(String message) {
        super(message);
    }
}
