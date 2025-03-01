package co.edu.uniquindio.pos_resturant_app.services.implementations;


import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteReadDTO;
import co.edu.uniquindio.pos_resturant_app.repository.IngredienteRepo;
import co.edu.uniquindio.pos_resturant_app.repository.UnidadMedidaRepo;
import co.edu.uniquindio.pos_resturant_app.repository.UsuarioRepo;
import co.edu.uniquindio.pos_resturant_app.services.specifications.IngredienteService;
import co.edu.uniquindio.pos_resturant_app.model.Ingrediente;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class IngredienteServiceImp implements IngredienteService {

    private final IngredienteRepo ingredienteRepo;
    private final UsuarioRepo usuarioRepo;
    private final UnidadMedidaRepo unidadMedidaRepo;


    @Override
    public int create(IngredienteCreateDTO dto) throws Exception {
        var entity = dto.toEntity();
        entity.setUnidadMedida(unidadMedidaRepo.findById(dto.unidad_medida()).orElseThrow(() -> new Exception("Unidad de medida no encontrada")));
        return ingredienteRepo.save(entity).getId();
    }

    @Override
    public boolean update(IngredienteReadDTO dto) throws Exception {
        var entityOptional = ingredienteRepo.findById(dto.id());
        if (entityOptional.isPresent()) {
            var ingrediente = entityOptional.get();
            ingrediente.setNombre(dto.nombre());
            ingrediente.setMarca(dto.marca());
            ingrediente.setPrecioCompra(dto.precioCompra());
            ingrediente.setCantidadDisponible(dto.cantidadDisponible());
            ingrediente.setUnidadMedida(unidadMedidaRepo.findById(dto.unidad_medida()).orElseThrow(() -> new Exception("Unidad de medida no encontrada")));
            ingredienteRepo.save(ingrediente);
            return true;
        }
        return false;
    }

    @Override
    public void delete(String id) throws Exception {

    }

    @Override
    public IngredienteCreateDTO findById(String id) throws Exception {
        return null;
    }

    @Override
    public List<Ingrediente> getAll() throws Exception {
        return ingredienteRepo.findAll();
    }

    @Override
    public boolean editStock(String id, int cantidad) throws Exception {
        var ingrediente = ingredienteRepo.findById(Integer.parseInt(id)).orElseThrow(() -> new Exception("Ingrediente no encontrado"));
        ingrediente.setCantidadDisponible(ingrediente.getCantidadDisponible() + cantidad);
        ingredienteRepo.save(ingrediente);
        return true;
    }
}
