package co.edu.uniquindio.pos_resturant_app.controllers;


import co.edu.uniquindio.pos_resturant_app.dto.mesero.LoginDTO;
import co.edu.uniquindio.pos_resturant_app.dto.usuario.UsuarioReadDTO;
import co.edu.uniquindio.pos_resturant_app.dto.web.MensajeDTO;
import co.edu.uniquindio.pos_resturant_app.repository.CocineroRepo;
import co.edu.uniquindio.pos_resturant_app.repository.MeseroRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
@Slf4j
public class LoginController {

    private final MeseroRepo meseroRepo;
    private final CocineroRepo cocineroRepo;
    private final FormattingConversionService mvcConversionService;

    @PostMapping("/mesero")
    public ResponseEntity<MensajeDTO<UsuarioReadDTO>> loginMesero(@Valid @RequestBody LoginDTO dto) {
        var optional = meseroRepo.findByUsername(dto.username());
        if (optional.isEmpty()) {
            log.warn("No existe un mesero con ese username");
            return ResponseEntity.status(400)
                    .body(new MensajeDTO<>(true,
                            new UsuarioReadDTO("-1", "", ""),
                            "No existe un mesero con el username " + dto.username()));
        }
        if (!optional.get().getPassword().equals(dto.password())) {
            log.warn("La contraseña es incorrecta");
            return ResponseEntity.status(400)
                    .body(new MensajeDTO<>(true,
                            new UsuarioReadDTO("-1", "", ""),
                            "La contraseña es incorrecta"));
        }

        var mesero = optional.get();
        var response = new UsuarioReadDTO(mesero.getCedula(), mesero.getNombre(), mesero.getUsername());
        return ResponseEntity.ok(new MensajeDTO<>(false, response, "Usuario entregado correctamente"));
    }

    @PostMapping
    public ResponseEntity<MensajeDTO<UsuarioReadDTO>> loginCocinero(@Valid @RequestBody LoginDTO dto) {
        var optional = cocineroRepo.findByUsername(dto.username());
        if (optional.isEmpty()) {
            log.warn("No existe un mesero con ese username");
            return ResponseEntity.status(400)
                    .body(new MensajeDTO<>(true,
                            new UsuarioReadDTO("-1", "", ""),
                            "No existe un mesero con el username " + dto.username()));
        }
        if (!optional.get().getPassword().equals(dto.password())) {
            log.warn("La contraseña es incorrecta");
            return ResponseEntity.status(400)
                    .body(new MensajeDTO<>(true,
                            new UsuarioReadDTO("-1", "", ""),
                            "La contraseña es incorrecta"));
        }
        var cocinero = optional.get();
        var response = new UsuarioReadDTO(cocinero.getCedula(), cocinero.getNombre(), cocinero.getUsername());
        return ResponseEntity.ok(new MensajeDTO<>(false, response, "Usuario entregado correctamente"));
    }
}

