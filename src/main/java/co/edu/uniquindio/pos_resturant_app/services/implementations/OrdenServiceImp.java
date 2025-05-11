package co.edu.uniquindio.pos_resturant_app.services.implementations;

import co.edu.uniquindio.pos_resturant_app.dto.joints.OrdenPlatoDTO;
import co.edu.uniquindio.pos_resturant_app.exceptions.RecordNotFoundException;
import co.edu.uniquindio.pos_resturant_app.model.Mesa;
import co.edu.uniquindio.pos_resturant_app.model.Orden;
import co.edu.uniquindio.pos_resturant_app.model.enums.EstadoOrden;
import co.edu.uniquindio.pos_resturant_app.model.joints.OrdenPlato;
import co.edu.uniquindio.pos_resturant_app.model.keys.OrdenPlatoID;
import co.edu.uniquindio.pos_resturant_app.repository.MesaRepo;
import co.edu.uniquindio.pos_resturant_app.repository.MeseroRepo;
import co.edu.uniquindio.pos_resturant_app.repository.OrdenRepo;
import co.edu.uniquindio.pos_resturant_app.repository.PlatoRepo;
import co.edu.uniquindio.pos_resturant_app.repository.joints.OrdenPlatoRepo;
import co.edu.uniquindio.pos_resturant_app.services.specifications.OrdenService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class OrdenServiceImp implements OrdenService {
    private final MesaRepo mesaRepo;
    private final MeseroRepo meseroRepo;
    private final OrdenRepo ordenRepo;
    private final PlatoRepo platoRepo;
    private final OrdenPlatoRepo ordenPlatoRepo;

    @Override

    public Integer create(Integer idMesa, String cedulaMesero) {

        var mesaEntity = mesaRepo.findById(idMesa).orElseThrow(
                () -> new RecordNotFoundException("Mesa con  id:" + idMesa + " no encontrado"));
        var meseroEntity = meseroRepo.findById(cedulaMesero).orElseThrow(
                () -> new RecordNotFoundException("Mesero con cedula:" + cedulaMesero + " no encontrado"));

        var ordenEntity = Orden.builder()
                .mesa(mesaEntity)
                .mesero(meseroEntity)
                .fechaInicio(LocalDateTime.now())
                .estado(EstadoOrden.PROCESO)
                .build();

        return ordenRepo.save(ordenEntity).getIdOrden();
    }

    boolean addDetail(OrdenPlatoDTO dto) {
        //lets check if dto.orden and dto.plato do exist in the database
        var ordenEntity = ordenRepo.findById(dto.idOrden()).orElseThrow(
                () -> new RecordNotFoundException("Orden con id:" + dto.idOrden() + " no encontrado"));

        var platoEntity = platoRepo.findById(dto.idPlato()).orElseThrow(
                () -> new RecordNotFoundException("Plato con id:" + dto.idPlato() + " no encontrado"));


        var embeddedId = new OrdenPlatoID();

        var entity = OrdenPlato.builder()
                .id(embeddedId)
                .orden(ordenEntity)
                .plato(platoEntity)
                .cantidad(dto.cantidad())
                .build();

        ordenPlatoRepo.save(entity);
        return true;
    }


}
