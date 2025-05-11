package co.edu.uniquindio.pos_resturant_app.model.enums;

public enum Finance {
    IMPUESTO(0.18d);
    Double value;

    Finance(double v) {
    }

    public double value() {
        return value;
    }
}
