package co.edu.uniquindio.pos_resturant_app.model.enums;

public enum Finance {
    IVA(0.19d);
    Double value;

    Finance(double v) {
    }

    public double value() {
        return value;
    }
}
