package co.edu.uniquindio.pos_resturant_app.model.enums;

import co.edu.uniquindio.pos_resturant_app.exceptions.NotAValidStateException;

public enum EstadoOrden {

    ESPERA("ESPERA"),
    PROCESO("PROCESO"),
    FINALIZADA("FINALIZADA"),
    CANCELADA("CANCELADA");

    EstadoOrden(String estado) {
    }

    public static EstadoOrden getEstado(String str) throws NotAValidStateException {
        try {
            return EstadoOrden.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotAValidStateException("El valor pasado para el estado de la orden no es valido.\nValor: " + str);
        }
    }
}
