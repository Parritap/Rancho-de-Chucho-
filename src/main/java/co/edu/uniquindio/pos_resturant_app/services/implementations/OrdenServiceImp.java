package co.edu.uniquindio.pos_resturant_app.services.implementations;

import co.edu.uniquindio.pos_resturant_app.dto.joints.OrdenPlatoDTO;
import co.edu.uniquindio.pos_resturant_app.dto.orden.OrdenCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.orden.OrdenReadDTO;
import co.edu.uniquindio.pos_resturant_app.exceptions.NotAValidStateException;
import co.edu.uniquindio.pos_resturant_app.exceptions.RecordNotFoundException;
import co.edu.uniquindio.pos_resturant_app.model.Orden;
import co.edu.uniquindio.pos_resturant_app.model.enums.EstadoOrden;
import co.edu.uniquindio.pos_resturant_app.model.enums.Finance;
import co.edu.uniquindio.pos_resturant_app.model.joints.OrdenPlato;
import co.edu.uniquindio.pos_resturant_app.model.keys.OrdenPlatoID;
import co.edu.uniquindio.pos_resturant_app.repository.MesaRepo;
import co.edu.uniquindio.pos_resturant_app.repository.MeseroRepo;
import co.edu.uniquindio.pos_resturant_app.repository.OrdenRepo;
import co.edu.uniquindio.pos_resturant_app.repository.PlatoRepo;
import co.edu.uniquindio.pos_resturant_app.repository.joints.OrdenPlatoRepo;
import co.edu.uniquindio.pos_resturant_app.services.specifications.OrdenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.env.EnvironmentEndpoint;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Yo a veces me pregunto si deberia hacer tantas validaciones... pero es que no me  gusta asumir.

/**
 * IMPORTANTE: En el contexto de esta clase DETALLE se refiere a la relacion entre orden y plato.
 * Es decir, detalle es un registro de la tabla orden_plato.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrdenServiceImp implements OrdenService {
    private final MesaRepo mesaRepo;
    private final MeseroRepo meseroRepo;
    private final OrdenRepo ordenRepo;
    private final PlatoRepo platoRepo;
    private final OrdenPlatoRepo ordenPlatoRepo;
    private final EnvironmentEndpoint environmentEndpoint;

    /**
     * Este metodo crea una orden y la asocia a una mesa y un mesero y la guarda en la base de datos.
     * La orden se crea en estado ESPERA.
     *
     * @param idMesa
     * @param cedulaMesero
     * @return El id de la orden generado por la base de datos.
     */

    private Orden create_aux(Integer idMesa, String cedulaMesero) {

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

        return ordenRepo.save(ordenEntity);
    }


    /**
     * Función que abre una orden con estado
     * DETALLE es un registro de la tabla orden_plato.
     * La tabla que estamos afectando aquí es Orden (creación) y OrdenPlato mediante
     * la adición de un nuevo registro que relaciona a la orden
     * con un nuevo plato y la respectiva cantidad del mismo.
     *
     * @param dto que contiene el id de la mesa, id del mesero y una lista de
     *            PlatiCantidadDTO con el id y la cantidad de cada plantillo ingresado
     * @return true si se pudo agregar el detalle a la orden.
     */
    @Override
    public Integer openOrden(OrdenCreateDTO dto) {

        //Creamos la intancia inicila vacía de la entidad.

        var ordenEntity = create_aux(dto.idMesa(), dto.cedulaMesero());

        dto.platillos().forEach(plato -> {

            //Check if platillo exists
            var platoEntity = platoRepo.findById(plato.idPlato()).orElseThrow(
                    () -> new RecordNotFoundException("Plato con id:" + plato.idPlato() + " no encontrado"));


            //If so, then create a ordenPlatoEntity
            var ordenPlatoEntity = OrdenPlato.builder()
                    .id(new OrdenPlatoID())
                    .plato(platoEntity)
                    .orden(ordenEntity)
                    .cantidad(plato.cantidad())
                    .build();

            ordenPlatoRepo.save(ordenPlatoEntity);
        });

        return ordenEntity.getIdOrden();
    }


    /**
     * Edita un detalle de la orden según el idPlato
     * Este método lo que hace es sobreescribir un detalle existente de una orden en especifico
     * Debe usarse cuando se cambie la cantidad de un plato.
     *
     * @param dto contiene el id de la orden, el id del plato y la nueva cantidad del mismo.
     * @return true, excepción de lo contrario.
     */
    @Override
    public boolean editQuantityDetail(OrdenPlatoDTO dto) {
        //lets check if dto.orden and dto.plato do exist in the database
        var ordenEntity = ordenRepo.findById(dto.idOrden()).orElseThrow(
                () -> new RecordNotFoundException("Orden con id:" + dto.idOrden() + " no encontrado"));

        if (ordenEntity.getEstado() == EstadoOrden.FINALIZADA ||
                ordenEntity.getEstado() == EstadoOrden.CANCELADA) throw new DataIntegrityViolationException(
                "La orden no puede ser modificada, su estado es: " + ordenEntity.getEstado()
        );

        var platoEntity = platoRepo.findById(dto.idPlato()).orElseThrow(
                () -> new RecordNotFoundException("Plato con id:" + dto.idPlato() + " no encontrado"));

        // La siguiente línea tiene una excepción (EntityNotFoundException) la cual es controlada por GlobalExceptionHandler.
        var ordenPlato = ordenPlatoRepo.findByIdPlatoAndIdOrden(platoEntity.getId_plato(), ordenEntity.getIdOrden());
        ordenPlato.setCantidad(dto.cantidad());
        ordenPlatoRepo.save(ordenPlato);

        return true;
    }

    @Override
    public Boolean closeOrden(Integer idOrden) {
        var entity = ordenRepo.findById(idOrden).orElseThrow(
                () -> new RecordNotFoundException("Orden con ID: " + idOrden + " no encontrada")
        );

        entity.setFechaCierre(LocalDateTime.now());

        var listaDetalles = ordenPlatoRepo.findByIdOrden(entity.getIdOrden());
        var subtotal = calcularSubTotal(listaDetalles);
        var impuestos = calcularImpuestos(subtotal);
        entity.setEstado(EstadoOrden.FINALIZADA);
        entity.setSubtotal(subtotal);
        entity.setImpuestos(impuestos);
        entity.setTotal(subtotal.add(impuestos));

        ordenRepo.save(entity);

        return true;
    }

    @Override
    public List<OrdenReadDTO> getOrdenes(int page, int pageSize) {
        PageRequest pageable = PageRequest.of(page, pageSize);
        //Obtengo una fracción de las ordenes mediante la paginación
        var listaOrdenes = ordenRepo.findAll(pageable).getContent();

        //Cada orden tiene un detalle, y en el detalle, por producto, su cantidad.
        Map<Orden, Integer> ordenCantidad = new HashMap<>();
        Map<Orden, List<OrdenPlato>> mapaDetalles = new HashMap<>();

        //Iteramos cada orden y obtenemos su registro de OrdenPlato
        listaOrdenes.forEach(orden -> {
            var detalles = ordenPlatoRepo.findByIdOrden(orden.getIdOrden());
            mapaDetalles.put(orden, detalles);
        });

        return listaOrdenes.stream().map(orden -> OrdenReadDTO.toDTO(orden, mapaDetalles.get(orden))).toList();
    }

    @Override
    public boolean editEstadoOrden(Integer idOrden, EstadoOrden newEstado) {
        log.info("Editando estado orden: " + idOrden);
        var entity = ordenRepo.findById(idOrden).orElseThrow(() ->
                new EntityNotFoundException("No existe el orden con ID " + idOrden));

        var estadoActual = entity.getEstado();
        if (estadoActual == EstadoOrden.FINALIZADA || estadoActual == EstadoOrden.CANCELADA) {
            throw new NotAValidStateException("Estado " + newEstado + " no valido para orden con estado " + estadoActual);
        }
        entity.setEstado(newEstado);
        return true;
    }

    private BigDecimal calcularSubTotal(List<OrdenPlato> listaDetalles) {
        // Calculate subtotal using streams instead of modifying a variable in lambda
        return listaDetalles.stream()
                .map(detalle -> BigDecimal.valueOf(detalle.getPlato().getPrecio())
                        .multiply(BigDecimal.valueOf(detalle.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calcularImpuestos(BigDecimal subtotal) {
        return subtotal.multiply(BigDecimal.valueOf(Finance.IVA.value()));
    }


}
