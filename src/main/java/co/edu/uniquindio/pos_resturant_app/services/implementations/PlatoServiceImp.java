package co.edu.uniquindio.pos_resturant_app.services.implementations;

import co.edu.uniquindio.pos_resturant_app.dto.plato.PlatoCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.plato.PlatoReadDTO;
import co.edu.uniquindio.pos_resturant_app.exceptions.CascadeEffectException;
import co.edu.uniquindio.pos_resturant_app.exceptions.RecordNotFoundException;
import co.edu.uniquindio.pos_resturant_app.model.Plato;
import co.edu.uniquindio.pos_resturant_app.model.TipoPlato;
import co.edu.uniquindio.pos_resturant_app.repository.PlatoRepo;
import co.edu.uniquindio.pos_resturant_app.repository.TipoPlatoRepo;
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


    @Override
    public int create(PlatoCreateDTO dto) throws RecordNotFoundException, Exception {

        // Check if tipoPlato exists
        // En teoría siempre debe exitir el tipo plato porque los tipos se cargan en el front con base a lo que hay en la db
        var tipoPlato = tipoPlatoRepo.findById(dto.id_tipo_plato()).orElseThrow(
                () -> new RecordNotFoundException("Tipo de plato no encontrado")
        );

        try {
            var entity = dto.toEntity();
            // Ensure the entity has the correct tipoPlato reference
            entity.setTipoPlato(tipoPlato);
            return platoRepo.save(entity).getId_plato();
        } catch (Exception e) {
            throw new Exception("Error creating dish: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean update(PlatoCreateDTO dto, int id) throws RecordNotFoundException, Exception {

        var platoEntity = platoRepo.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("PLATO: " + id));

        // Update tipoPlato if necessary
        var tipoPlato = tipoPlatoRepo.findById(dto.id_tipo_plato())
                .orElseThrow(() -> new RecordNotFoundException("TIPO PLATO: " + dto.id_tipo_plato()));// TODO Esto en teoría nunca debería de suceder

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
            if(!plato.isActivo()){
                plato.setActivo(true);
                platoRepo.save(plato);
            }else{
                throw new RecordNotFoundException("El plato ya se encuentra eliminado: " + id);
            }
        } else {
            throw new RecordNotFoundException("PLATO con ID: " + id);
        }

        return true;
    }

    @Override
    public PlatoCreateDTO findById(Integer id) throws RecordNotFoundException, Exception {
        Plato plato = platoRepo.findById(id)
            .orElseThrow(() -> new RecordNotFoundException("PLATO: " + id));
        return new PlatoCreateDTO(
            plato.getNombre(),
            plato.getDescripcion(),
            plato.getPrecio(),
            plato.getTipoPlato().getId_tipo_plato()
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
}
