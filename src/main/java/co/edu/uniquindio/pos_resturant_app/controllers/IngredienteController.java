package co.edu.uniquindio.pos_resturant_app.controllers;


import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteCreateDTO;
import co.edu.uniquindio.pos_resturant_app.services.specifications.IngredienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingrediente")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    public boolean create(@Valid @RequestBody IngredienteCreateDTO dto) throws Exception {
        ingredienteService.create(dto);
        return false;
    }


    @PutMapping("/{id}/update")
    public boolean editStock (@PathVariable String id, int cantidad) throws Exception {
        ingredienteService.editStock(id, cantidad);
        return false;
    }

    //Maps add function addToStock in the front end.
    @PutMapping("/{id}/{cantidad}/update")
    public boolean addToStock (@PathVariable String id, @PathVariable int cantidad) throws Exception {
        ingredienteService.editStock(id, cantidad);
        return false;
    }




}
