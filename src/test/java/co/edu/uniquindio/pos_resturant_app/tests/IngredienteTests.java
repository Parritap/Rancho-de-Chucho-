package co.edu.uniquindio.pos_resturant_app.tests;

import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteCreateDTO;
import co.edu.uniquindio.pos_resturant_app.model.Ingrediente;
import co.edu.uniquindio.pos_resturant_app.model.UnidadMedida;
import co.edu.uniquindio.pos_resturant_app.repository.IngredienteRepo;
import co.edu.uniquindio.pos_resturant_app.repository.UnidadMedidaRepo;
import co.edu.uniquindio.pos_resturant_app.services.implementations.UnidadMedidaServiceImp;
import co.edu.uniquindio.pos_resturant_app.services.specifications.IngredienteService;
import co.edu.uniquindio.pos_resturant_app.services.specifications.UnidadMedidaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;


@SpringBootTest
public class IngredienteTests {

    @Autowired
    private IngredienteService ingredienteService;
    @Autowired
    private UnidadMedidaService unidadMedidaService;
    @Autowired
    private IngredienteRepo ingredienteRepo;
    @Autowired
    private UnidadMedidaRepo unidadMedidaRepo;

    @Test
    void setUp() {

        System.out.println("Before all tests");
        UnidadMedida kg = UnidadMedida.builder().notacion("kg").nombre("Kilogramos").build();
        UnidadMedida gr = UnidadMedida.builder().notacion("gr").nombre("Gramos").build();
        UnidadMedida lt = UnidadMedida.builder().notacion("lt").nombre("Litros").build();

        try {
            System.out.println((unidadMedidaService.create(kg)));
            System.out.println((unidadMedidaService.create(gr)));
            System.out.println((unidadMedidaService.create(lt)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void entityCreationTest() {
        UnidadMedida kg = UnidadMedida.builder().notacion("kg").nombre("Kilogramos").build();
        UnidadMedida gr = UnidadMedida.builder().notacion("gr").nombre("Gramos").build();
        UnidadMedida lt = UnidadMedida.builder().notacion("lt").nombre("Litros").build();

        try {
            System.out.println((unidadMedidaService.create(kg)));
            System.out.println((unidadMedidaService.create(gr)));
            System.out.println((unidadMedidaService.create(lt)));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sample Ingrediente objects using the builder
        Ingrediente azucar = Ingrediente.builder()
                .nombre("Azúcar")
                .marca("Ledesma")
                .precioCompra(new BigDecimal("1.50"))
                .cantidadDisponible(50)
                .unidadMedida(kg)
                .build();

        Ingrediente aceite = Ingrediente.builder()
                .nombre("Aceite de Girasol")
                .marca("Cocinero")
                .precioCompra(new BigDecimal("2.20"))
                .cantidadDisponible(20)
                .unidadMedida(lt)
                .build();

        Ingrediente sal = Ingrediente.builder()
                .nombre("Sal")
                .marca("Celusal")
                .precioCompra(new BigDecimal("0.80"))
                .cantidadDisponible(100)
                .unidadMedida(kg)
                .build();

        //saves into the database
        ingredienteRepo.save(azucar);
        ingredienteRepo.save(aceite);
        ingredienteRepo.save(sal);
    }

    @Test
    public void dtosTest() {
        IngredienteCreateDTO dto = new IngredienteCreateDTO(
                "",
                "",
                new BigDecimal("1.50"),
                50,
                "kg");
        var entity = dto.toEntity();
        var unidadOptional = unidadMedidaRepo.findById(dto.unidad_medida());
        if (unidadOptional.isPresent()) {
            entity.setUnidadMedida(unidadOptional.get());
        }
        System.out.println(entity.toString());
    }


}
