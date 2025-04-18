package co.edu.uniquindio.pos_resturant_app.services.implementations;

import co.edu.uniquindio.pos_resturant_app.dto.tipoPlato.TipoPlatoReadDTO;
import co.edu.uniquindio.pos_resturant_app.model.TipoPlato;
import co.edu.uniquindio.pos_resturant_app.repository.TipoPlatoRepo;
import co.edu.uniquindio.pos_resturant_app.services.specifications.TipoPlatoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TipoPlatoServiceImp implements TipoPlatoService {

    private final TipoPlatoRepo tipoPlatoRepo;


    @Override
    public List<TipoPlatoReadDTO> listarTiposPlato() {
        return tipoPlatoRepo.findAll().stream()
                .map(entity -> TipoPlatoReadDTO.builder()
                        .id(entity.getId_tipo_plato())
                        .nombre(entity.getNombre())
                        .descripcion(entity.getDescripcion())

                        .idTipoPadre(Optional.ofNullable(entity.getTipoPadre())
                                .map(TipoPlato::getId_tipo_plato)
                                .orElse(null))

                        .build())
                .toList();
    }

    /**
     * Genera un JSON con la jerarquía de tipos de plato.
     * @return JSON con la jerarquía de tipos de plato.
     */
    public String obtenerJerarquiaTiposPlato() {
        // Fetch all TipoPlato entities
        List<TipoPlato> todosTipos = tipoPlatoRepo.findAll();

        // Create a map for organizing by parent
        Map<Integer, List<TipoPlato>> hijosPorPadre = new HashMap<>();
        List<TipoPlato> tiposRaiz = new ArrayList<>();

        // Organize entities by parent
        for (TipoPlato tipo : todosTipos) {
            if (tipo.getTipoPadre() == null) {
                tiposRaiz.add(tipo);
            } else {
                int idPadre = tipo.getTipoPadre().getId_tipo_plato();
                hijosPorPadre.computeIfAbsent(idPadre, k -> new ArrayList<>()).add(tipo);
            }
        }

        // Build JSON
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < tiposRaiz.size(); i++) {
            if (i > 0) {
                json.append(",");
            }
            construirJSONTipoPlato(tiposRaiz.get(i), hijosPorPadre, json);
        }
        json.append("]");

        return json.toString();
    }

    private void construirJSONTipoPlato(TipoPlato tipo, Map<Integer, List<TipoPlato>> hijosPorPadre, StringBuilder json) {
        json.append("{");
        json.append("\"id\":").append(tipo.getId_tipo_plato()).append(",");
        json.append("\"nombre\":\"").append(escaparJSON(tipo.getNombre())).append("\",");
        json.append("\"descripcion\":\"").append(escaparJSON(tipo.getDescripcion() != null ? tipo.getDescripcion() : "")).append("\"");

        List<TipoPlato> hijos = hijosPorPadre.get(tipo.getId_tipo_plato());
        if (hijos != null && !hijos.isEmpty()) {
            json.append(",\"subtipos\":[");
            for (int i = 0; i < hijos.size(); i++) {
                if (i > 0) {
                    json.append(",");
                }
                construirJSONTipoPlato(hijos.get(i), hijosPorPadre, json);
            }
            json.append("]");
        }

        json.append("}");
    }

    private String escaparJSON(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}
