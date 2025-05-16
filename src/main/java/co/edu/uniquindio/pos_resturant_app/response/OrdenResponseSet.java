package co.edu.uniquindio.pos_resturant_app.response;


import co.edu.uniquindio.pos_resturant_app.dto.orden.OrdenReadDTO;
import co.edu.uniquindio.pos_resturant_app.model.Orden;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.service.annotation.GetExchange;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@Setter
public class OrdenResponseSet<Atom> extends ResponseSet {
    @Builder.Default
    private List<Atom> ordenes = List.of();
}

