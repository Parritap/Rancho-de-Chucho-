package co.edu.uniquindio.pos_resturant_app.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Cocinero extends Usuario implements Serializable {

}
