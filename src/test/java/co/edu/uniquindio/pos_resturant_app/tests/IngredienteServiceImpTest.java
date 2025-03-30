package co.edu.uniquindio.pos_resturant_app.tests;

import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteReadDTO;
import co.edu.uniquindio.pos_resturant_app.exceptions.RecordNotFoundException;
import co.edu.uniquindio.pos_resturant_app.model.Ingrediente;
import co.edu.uniquindio.pos_resturant_app.model.UnidadMedida;
import co.edu.uniquindio.pos_resturant_app.repository.IngredienteRepo;
import co.edu.uniquindio.pos_resturant_app.repository.UnidadMedidaRepo;
import co.edu.uniquindio.pos_resturant_app.repository.joints.IngredientePlatoRepo;
import co.edu.uniquindio.pos_resturant_app.services.implementations.IngredienteServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredienteServiceImpTest {

    @Mock
    private IngredienteRepo ingredienteRepo;

    @Mock
    private UnidadMedidaRepo unidadMedidaRepo;

    @Mock
    private IngredientePlatoRepo ingredientePlatoRepo;

    @InjectMocks
    private IngredienteServiceImp ingredienteService;

    private IngredienteCreateDTO createDTO;
    private Ingrediente ingrediente;
    private UnidadMedida unidadMedida;

    @BeforeEach
    void setUp() {
        createDTO = new IngredienteCreateDTO(
                "Sal",
                "La Fina",
                new BigDecimal("5000"),
                100,
                "kg"
        );

        unidadMedida = UnidadMedida.builder()
                .notacion("kg")
                .nombre("Kilogramos")
                .build();

        ingrediente = Ingrediente.builder()
                .id(1)
                .nombre("Sal")
                .marca("La Fina")
                .precioCompra(new BigDecimal("5000"))
                .cantidadDisponible(100)
                .unidadMedida(unidadMedida)
                .build();
    }

    @Test
    void create_Success() throws Exception {
        // Arrange
        when(unidadMedidaRepo.findById("kg")).thenReturn(Optional.of(unidadMedida));
        when(ingredienteRepo.save(any(Ingrediente.class))).thenReturn(ingrediente);

        // Act
        int result = ingredienteService.create(createDTO);

        // Assert
        assertEquals(1, result);
        verify(unidadMedidaRepo).findById("kg");
        verify(ingredienteRepo).save(any(Ingrediente.class));
    }

    @Test
    void create_UnitOfMeasureNotFound() {
        // Arrange
        when(unidadMedidaRepo.findById("kg")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> {
            ingredienteService.create(createDTO);
        });
        verify(unidadMedidaRepo).findById("kg");
        verify(ingredienteRepo, never()).save(any(Ingrediente.class));
    }

    @Test
    void addStock_Success() throws Exception {
        // Arrange
        when(ingredienteRepo.findById(1)).thenReturn(Optional.of(ingrediente));
        when(ingredienteRepo.save(ingrediente)).thenReturn(ingrediente);

        // Act
        boolean result = ingredienteService.addStock("1", 50);

        // Assert
        assertTrue(result);
        assertEquals(150, ingrediente.getCantidadDisponible());
        verify(ingredienteRepo).findById(1);
        verify(ingredienteRepo).save(ingrediente);
    }




    @Test
    void update_Success() throws Exception {
        // Arrange
        when(ingredienteRepo.findById(1)).thenReturn(Optional.of(ingrediente));
        when(unidadMedidaRepo.findById("kg")).thenReturn(Optional.of(unidadMedida));
        when(ingredienteRepo.save(any(Ingrediente.class))).thenReturn(ingrediente);

        // Act
        boolean result = ingredienteService.update(createDTO, 1);

        // Assert
        assertTrue(result);
        verify(ingredienteRepo).findById(1);
        verify(unidadMedidaRepo).findById("kg");
        verify(ingredienteRepo).save(any(Ingrediente.class));
    }



    @Test
    void update_UnitOfMeasureNotFound() {
        // Arrange
        when(ingredienteRepo.findById(1)).thenReturn(Optional.of(ingrediente));
        when(unidadMedidaRepo.findById("kg")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> {
            ingredienteService.update(createDTO, 1);
        });
        verify(ingredienteRepo).findById(1);
        verify(unidadMedidaRepo).findById("kg");
        verify(ingredienteRepo, never()).save(any(Ingrediente.class));
    }


    @Test
    void getAll_Success() throws Exception {
        // Arrange
        List<Ingrediente> ingredientesList = List.of(ingrediente);
        when(ingredienteRepo.findAll()).thenReturn(ingredientesList);

        // Act
        List<IngredienteReadDTO> result = ingredienteService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ingrediente.getNombre(), result.get(0).nombre());
        verify(ingredienteRepo).findAll();
    }

    @Test
    void getAll_EmptyList() throws Exception {
        // Arrange
        when(ingredienteRepo.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<IngredienteReadDTO> result = ingredienteService.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(ingredienteRepo).findAll();
    }
}