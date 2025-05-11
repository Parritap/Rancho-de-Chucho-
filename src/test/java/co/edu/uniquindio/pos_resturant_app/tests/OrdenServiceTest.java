package co.edu.uniquindio.pos_resturant_app.tests;

        import co.edu.uniquindio.pos_resturant_app.model.Mesa;
        import co.edu.uniquindio.pos_resturant_app.model.Mesero;
        import co.edu.uniquindio.pos_resturant_app.model.Orden;
        import co.edu.uniquindio.pos_resturant_app.model.enums.EstadoOrden;
        import co.edu.uniquindio.pos_resturant_app.repository.MesaRepo;
        import co.edu.uniquindio.pos_resturant_app.repository.MeseroRepo;
        import co.edu.uniquindio.pos_resturant_app.repository.OrdenRepo;
        import co.edu.uniquindio.pos_resturant_app.services.implementations.OrdenServiceImp;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.junit.jupiter.MockitoExtension;

        import java.time.LocalDateTime;
        import java.util.Optional;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.mockito.ArgumentMatchers.any;
        import static org.mockito.ArgumentMatchers.anyInt;
        import static org.mockito.ArgumentMatchers.anyString;
        import static org.mockito.Mockito.verify;
        import static org.mockito.Mockito.when;

        @ExtendWith(MockitoExtension.class)
        public class OrdenServiceTest {

            @Mock
            private MesaRepo mesaRepo;

            @Mock
            private MeseroRepo meseroRepo;

            @Mock
            private OrdenRepo ordenRepo;

            @InjectMocks
            private OrdenServiceImp ordenService;

            private Mesa mesa;
            private Mesero mesero;
            private Orden orden;


            @BeforeEach
            void setUp() {

                mesa = Mesa.builder()
                        .id(1)
                        .disponible(true).
                        build();


                mesero = Mesero.builder()
                        .nombre("Juan el mesero")
                        .cedula("1111111111")
                        .estaActivo(true)
                        .username("donJuan_mesero")
                        .build();


                orden = Orden.builder()
                        .idOrden(100) // Assuming the saved order will have an ID
                        .mesa(mesa)
                        .mesero(mesero)
                        .fechaInicio(LocalDateTime.now())
                        .estado(EstadoOrden.PROCESO)
                        .build();

                when(mesaRepo.findById(anyInt())).thenReturn(Optional.of(mesa));
                when(meseroRepo.findById(anyString())).thenReturn(Optional.of(mesero));
                when(ordenRepo.save(any(Orden.class))).thenReturn(orden);
            }


            @Test
            void create_Success() {
                // Arrange
                Integer idMesa = 1;
                String cedulaMesero = "1111111111";

                // Act
                Integer ordenId = ordenService.create(idMesa, cedulaMesero);
                System.out.println(ordenId);

                // Assert
                assertEquals(orden.getIdOrden(), ordenId); // Check if the returned ID is correct
                verify(mesaRepo).findById(idMesa); // Verify mesaRepo.findById was called with the correct ID
                verify(meseroRepo).findById(cedulaMesero); // Verify meseroRepo.findById was called with the correct cedula
                verify(ordenRepo).save(any(Orden.class)); // Verify ordenRepo.save was called
            }
        }