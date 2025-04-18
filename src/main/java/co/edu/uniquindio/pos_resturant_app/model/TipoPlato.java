package co.edu.uniquindio.pos_resturant_app.model;
import co.edu.uniquindio.pos_resturant_app.dto.tipoPlato.TipoPlatoCreateDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class TipoPlato implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id_tipo_plato;

    @Column ( nullable = false)
    private String nombre;

    private String descripcion;

    @OneToMany (mappedBy = "tipoPlato")
    private List<Plato> listaPlatos;

    @ManyToOne
    @JoinColumn (name = "tipo_padre", referencedColumnName = "id_tipo_plato", nullable = true)
    private TipoPlato tipoPadre;

    public TipoPlatoCreateDTO toTipoPlatoCreateDTO() {
        return TipoPlatoCreateDTO.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .idTipoPadre(id_tipo_plato)
                .build();
    }
}
