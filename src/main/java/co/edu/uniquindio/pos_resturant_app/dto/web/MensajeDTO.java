package co.edu.uniquindio.pos_resturant_app.dto.web;

public record MensajeDTO<T>(
        boolean error,
        T respuesta
) {
    public MensajeDTO(T respuesta) {
        this(false, respuesta);
    }

    public MensajeDTO(boolean error, T respuesta) {
        this.error = error;
        this.respuesta = respuesta;
    }
}
