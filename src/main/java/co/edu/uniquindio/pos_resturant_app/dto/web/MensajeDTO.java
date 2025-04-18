package co.edu.uniquindio.pos_resturant_app.dto.web;


/**
 * Clase que representa un mensaje de respuesta para el cliente.
 * @param error Debe ser true si hubo un error en la peticion, false de lo contrario.
 * @param respuesta El objeto que se va a enviar como respuesta al cliente.
 * @param <T> El tipo de objeto que se va a enviar como respuesta al cliente.
 */
public record MensajeDTO<T>(
        boolean error,
        T respuesta,
        String mensaje
) {
    // Single-parameter constructor
    public MensajeDTO(T respuesta) {
        this(false, respuesta, "");
    }

    // Two-parameter constructor
    public MensajeDTO(boolean error, T respuesta) {
        this(error, respuesta, "");
    }

    // The canonical constructor is being overridden but has an error
    public MensajeDTO(boolean error, T respuesta, String mensaje) {
        this.error = error;
        this.respuesta = respuesta;
        this.mensaje = mensaje; // Should use the parameter, not hardcode ""
    }
}
