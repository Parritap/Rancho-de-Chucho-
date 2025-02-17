package co.edu.uniquindio.pos_resturant_app.model;

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
public class Mesero extends Usuario implements Serializable {

    @Column (nullable = false)
    private Boolean enTurno = false;

    @OneToMany(mappedBy = "mesero")
    private List<Orden> ordenes;
}
