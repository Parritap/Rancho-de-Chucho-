package co.edu.uniquindio.pos_resturant_app.tests;

import co.edu.uniquindio.pos_resturant_app.controllers.IngredienteController;
import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteReadDTO;
import co.edu.uniquindio.pos_resturant_app.dto.web.MensajeDTO;
import co.edu.uniquindio.pos_resturant_app.exceptions.CascadeEffectException;
import co.edu.uniquindio.pos_resturant_app.exceptions.RecordNotFoundException;
import co.edu.uniquindio.pos_resturant_app.services.specifications.IngredienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class IngredienteControllerTest {

    @Mock
    private IngredienteService ingredienteService;

    @InjectMocks
    private IngredienteController ingredienteController;

    private IngredienteCreateDTO validCreateDto;
    private List<IngredienteReadDTO> ingredientes;

    @BeforeEach
    void setUp() {
        validCreateDto = new IngredienteCreateDTO(
                "Sal",
                "La Fina",
                new BigDecimal("5000"),
                100,
                "kg"
        );

        ingredientes = List.of(
            new IngredienteReadDTO(1, "Sal", "La Fina", new BigDecimal("5000"), 100, "kg"),
            new IngredienteReadDTO(2, "Azúcar", "El Dorado", new BigDecimal("4000"), 150, "kg")
        );
    }

    @Test
    void create_Success() throws Exception {
        // Arrange
        when(ingredienteService.create(validCreateDto)).thenReturn(1);

        // Act
        ResponseEntity<MensajeDTO<Integer>> response = ingredienteController.create(validCreateDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertFalse(response.getBody().error());
        assertEquals(1, response.getBody().respuesta());
        verify(ingredienteService).create(validCreateDto);
    }

    @Test
    void create_UnitMeasureNotFound() throws Exception {
        // Arrange
        when(ingredienteService.create(validCreateDto)).thenThrow(new RecordNotFoundException("Unidad de medida no encontrada"));

        // Act
        ResponseEntity<MensajeDTO<Integer>> response = ingredienteController.create(validCreateDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().error());
        assertEquals(-1, response.getBody().respuesta());
    }

    @Test
    void create_UnexpectedError() throws Exception {
        // Arrange
        when(ingredienteService.create(validCreateDto)).thenThrow(new RuntimeException("Unexpected error"));

        // Act
        ResponseEntity<MensajeDTO<Integer>> response = ingredienteController.create(validCreateDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().error());
        assertEquals(-1, response.getBody().respuesta());
    }

    @Test
    void editStock_Success() throws Exception {
        // Arrange
        when(ingredienteService.addStock("1", 50)).thenReturn(true);

        // Act
        ResponseEntity<MensajeDTO<Boolean>> response = ingredienteController.editStock("1", 50);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().error());
        assertFalse(response.getBody().respuesta());
    }

    @Test
    void editStock_NotFound() throws Exception {
        // Arrange
        when(ingredienteService.addStock("999", 50)).thenReturn(false);

        // Act
        ResponseEntity<MensajeDTO<Boolean>> response = ingredienteController.editStock("999", 50);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void update_Success() throws Exception {
        // Arrange
        when(ingredienteService.update(validCreateDto, 1)).thenReturn(true);

        // Act
        ResponseEntity<MensajeDTO<Boolean>> response = ingredienteController.update(1, validCreateDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().error());
        assertFalse(response.getBody().respuesta());
    }

    @Test
    void update_NotFound() throws Exception {
        // Arrange
        when(ingredienteService.update(validCreateDto, 999)).thenThrow(new RecordNotFoundException("Ingrediente no encontrado"));

        // Act
        ResponseEntity<MensajeDTO<Boolean>> response = ingredienteController.update(999, validCreateDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().error());
        assertFalse(response.getBody().respuesta());
    }

    @Test
    void update_UnexpectedError() throws Exception {
        // Arrange
        when(ingredienteService.update(validCreateDto, 1)).thenThrow(new RuntimeException("Unexpected error"));

        // Act
        ResponseEntity<MensajeDTO<Boolean>> response = ingredienteController.update(1, validCreateDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody().error());
        assertFalse(response.getBody().respuesta());
    }

    @Test
    void delete_Success() throws Exception {
        // Arrange
        when(ingredienteService.delete(1)).thenReturn(true);

        // Act
        ResponseEntity<MensajeDTO<Boolean>> response = ingredienteController.delete(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().error());
        assertTrue(response.getBody().respuesta());
    }

    @Test
    void delete_NotFound() throws Exception {
        // Arrange
        when(ingredienteService.delete(999)).thenThrow(new RecordNotFoundException("Ingrediente no encontrado"));

        // Act
        ResponseEntity<MensajeDTO<Boolean>> response = ingredienteController.delete(999);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().error());
        assertFalse(response.getBody().respuesta());
    }

    @Test
    void delete_CascadeEffect() throws Exception {
        // Arrange
        when(ingredienteService.delete(1)).thenThrow(new CascadeEffectException("Ingrediente usado en recetas"));

        // Act
        ResponseEntity<MensajeDTO<Boolean>> response = ingredienteController.delete(1);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertFalse(response.getBody().error());
        assertFalse(response.getBody().respuesta());
    }

    @Test
    void delete_UnexpectedError() throws Exception {
        // Arrange
        when(ingredienteService.delete(1)).thenThrow(new RuntimeException("Unexpected error"));

        // Act
        ResponseEntity<MensajeDTO<Boolean>> response = ingredienteController.delete(1);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody().error());
        assertFalse(response.getBody().respuesta());
    }

    @Test
    void getAll_Success() throws Exception {
        // Arrange
        when(ingredienteService.getAll()).thenReturn(ingredientes);

        // Act
        ResponseEntity<MensajeDTO<List<IngredienteReadDTO>>> response = ingredienteController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().error());
        assertEquals(ingredientes, response.getBody().respuesta());
        assertEquals(2, response.getBody().respuesta().size());
    }

    @Test
    void getAll_UnexpectedError() throws Exception {
        // Arrange
        when(ingredienteService.getAll()).thenThrow(new RuntimeException("Unexpected error"));

        // Act
        ResponseEntity<MensajeDTO<List<IngredienteReadDTO>>> response = ingredienteController.getAll();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().error());
        assertNull(response.getBody().respuesta());
    }
}
