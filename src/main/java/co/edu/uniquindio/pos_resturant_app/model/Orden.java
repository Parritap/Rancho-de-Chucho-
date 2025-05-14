package co.edu.uniquindio.pos_resturant_app.model;

import co.edu.uniquindio.pos_resturant_app.model.enums.EstadoOrden;
import co.edu.uniquindio.pos_resturant_app.model.joints.OrdenPlato;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor // Necesario para que Spring pueda instanciar la clase.
@AllArgsConstructor
@SuperBuilder
public class Orden implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrden;

    @ManyToOne
    @JoinColumn(name = "id_mesa",nullable = false)  // Foreign Key to 'mesa'
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "cedula_mesero", referencedColumnName = "cedula", nullable = false) // Foreign Key to 'mesero'
    private Mesero mesero;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column
    private LocalDateTime fechaCierre;  // Nullable, as per your model

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal subtotal = BigDecimal.ZERO; //Valor con el que empieza la orden al ser creada.

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal impuestos = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrdenPlato> listaDetalles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoOrden estado;
}
