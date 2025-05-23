package co.edu.uniquindio.pos_resturant_app.services.implementations;

import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteDetailDTO;
import co.edu.uniquindio.pos_resturant_app.dto.joints.IngredienteAtom;
import co.edu.uniquindio.pos_resturant_app.dto.joints.IngredientesPlatoDTO;
import co.edu.uniquindio.pos_resturant_app.dto.plato.PlatoCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.plato.PlatoReadDTO;
import co.edu.uniquindio.pos_resturant_app.exceptions.CascadeEffectException;
import co.edu.uniquindio.pos_resturant_app.exceptions.DuplicatedRecordException;
import co.edu.uniquindio.pos_resturant_app.exceptions.RecordNotFoundException;
import co.edu.uniquindio.pos_resturant_app.model.Plato;
import co.edu.uniquindio.pos_resturant_app.model.joints.IngredientePlato;
import co.edu.uniquindio.pos_resturant_app.model.keys.IngredientePlatoID;
import co.edu.uniquindio.pos_resturant_app.repository.IngredienteRepo;
import co.edu.uniquindio.pos_resturant_app.repository.PlatoRepo;
import co.edu.uniquindio.pos_resturant_app.repository.TipoPlatoRepo;
import co.edu.uniquindio.pos_resturant_app.repository.UnidadMedidaRepo;
import co.edu.uniquindio.pos_resturant_app.repository.joints.IngredientePlatoRepo;
import co.edu.uniquindio.pos_resturant_app.services.specifications.PlatoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlatoServiceImp implements PlatoService {

    private final PlatoRepo platoRepo;
    private final TipoPlatoRepo tipoPlatoRepo;
    private final IngredientePlatoRepo ingredientePlatoRepo;
    private final IngredienteRepo ingredienteRepo;
    private final UnidadMedidaRepo unidadMedidaRepo;


    @Override
    public Integer create(PlatoCreateDTO dto) throws RecordNotFoundException, Exception {

        //Verificamos si el plato no es un duplicado
        if (!platoRepo.findByNombre(dto.nombre()).isEmpty()) {
            throw new DuplicatedRecordException("Ya existe un plato con el nombre: " + dto.nombre());
        }

        // Check if tipoPlato exists
        // En teoría siempre debe exitir el tipo plato porque los tipos se cargan en el front con base a lo que hay en la db
        tipoPlatoRepo.findById(dto.id_tipo_plato()).orElseThrow(
                () -> new RecordNotFoundException("Tipo de plato con ID " + dto.id_tipo_plato() + " no encontrado")
        );

        // Agregamos el plato a la basede datos para luego hacer la relación en la tabla IngredientePlato
        Plato plato = dto.toEntity();
        plato.setTipoPlato(tipoPlatoRepo.findById(dto.id_tipo_plato()).get());
        var platoEntity = platoRepo.save(plato);

        // Por cada IngredienteAtom encontrado en el PlatoCreateDTO agregamos la relación en IngredientePlato
        for (IngredienteAtom atom : dto.listaIngredientes()) {

            var unidadMedidadEntity = unidadMedidaRepo.findById(atom.notacionUnidadMedida()).orElseThrow(
                    () -> new RecordNotFoundException("Unidad de medida  " + atom.notacionUnidadMedida() + " no encontrado")
            );

            var ingredienteEntity = ingredienteRepo.findById(atom.idIngrediente()).orElseThrow(
                    () -> new RecordNotFoundException("Ingrediente " + atom.idIngrediente() + " no encontrado")
            );
            var ingredientePlato = IngredientePlato.builder()
                    .id(new IngredientePlatoID())
                    .ingrediente(ingredienteEntity)
                    .plato(platoEntity)
                    .cantidad(atom.cantidad())
                    .unidadMedida(unidadMedidadEntity)
                    .build();

            ingredientePlatoRepo.save(ingredientePlato);
        }
        return platoEntity.getId_plato();
    }

    @Override
    public boolean update(PlatoCreateDTO dto, int id) throws RecordNotFoundException, Exception {

        var platoEntity = platoRepo.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("PLATO: " + id));

        // Update tipoPlato if necessary
        var tipoPlato = tipoPlatoRepo.findById(dto.id_tipo_plato())
                .orElseThrow(() -> new RecordNotFoundException("TIPO PLATO: " + dto.id_tipo_plato()));//

        try {
            platoEntity.setNombre(dto.nombre());
            platoEntity.setDescripcion(dto.descripcion());
            platoEntity.setPrecio(dto.precio());
            platoEntity.setTipoPlato(tipoPlato);

            platoRepo.save(platoEntity); // Aquí sí haces un UPDATE real

            return true;
        } catch (Exception e) {
            throw new Exception("Error updating dish: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Integer id) throws CascadeEffectException, RecordNotFoundException, Exception {
        if (platoRepo.existsById(id)) {
            var plato = platoRepo.findById(id).orElseThrow(() ->
                    new RecordNotFoundException("PLATO: " + id));
            if (plato.isActivo()) {
                throw new CascadeEffectException("No se puede eliminar el plato porque está en uso.");
            }
            platoRepo.delete(plato);
        } else {
            throw new RecordNotFoundException("PLATO: " + id);
        }

        return true;
    }

    @Override
    public boolean logicalDelete(Integer id) throws RecordNotFoundException, Exception {
        var entityOptional = platoRepo.findById(id);
        if (entityOptional.isPresent()) {
            var plato = entityOptional.get();
            if (!plato.isActivo()) {
                plato.setActivo(true);
                platoRepo.save(plato);
            } else {
                throw new RecordNotFoundException("El plato ya se encuentra eliminado: " + id);
            }
        } else {
            throw new RecordNotFoundException("PLATO con ID: " + id);
        }

        return true;
    }

    @Override
    public PlatoReadDTO findById(Integer id) throws RecordNotFoundException, Exception {
        Plato plato = platoRepo.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("PLATO: " + id));
        return new PlatoReadDTO(
                plato.getId_plato(),
                plato.getNombre(),
                plato.getDescripcion(),
                plato.getPrecio(),
                plato.getTipoPlato().getNombre(),
                plato.isActivo()
        );
    }

    @Override
    public List<PlatoReadDTO> getAll() throws Exception {
        return platoRepo.findAll().stream().map(Plato::toReadDTO).toList();
    }

    @Override
    public boolean cambiarEstado(Integer id, boolean nuevo_estado) throws RecordNotFoundException, Exception {
        var entityOptional = platoRepo.findById(id);
        if (entityOptional.isPresent()) {
            var plato = entityOptional.get();
            plato.setActivo(nuevo_estado);
            platoRepo.save(plato);
            return true;
        } else {
            throw new RecordNotFoundException("PLATO con ID: " + id);
        }
    }

    @Override
    public IngredientesPlatoDTO getIngredientes(Integer idPlato) {
        var ingredientes = ingredientePlatoRepo.findByPlatoId(idPlato);
        var listaDetalles = ingredientes.stream().map(e ->
                new IngredienteDetailDTO(
                        e.getPlato().getId_plato(),
                        e.getPlato().getNombre())
        ).toList();
        return new IngredientesPlatoDTO(listaDetalles);
    }
}
