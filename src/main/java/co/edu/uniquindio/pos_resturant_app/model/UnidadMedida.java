package co.edu.uniquindio.pos_resturant_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
public class UnidadMedida implements Serializable {

    @Id
    private String notacion; //Por ejemplo L para litros

    @Column (nullable = false)
    private String nombre; // Aquí irían los litros.

    @OneToMany (mappedBy = "unidadMedida")
    private List<Ingrediente> listaIngredientes;
}
