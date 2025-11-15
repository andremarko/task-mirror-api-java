package task.mirror.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.mirror.api.dto.response.TipoTarefaResponseDTO;
import task.mirror.api.service.TipoTarefaService;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-tarefa")
public class TipoTarefaController {

    @Autowired
    private TipoTarefaService tipoTarefaService;

    @Tag(name = "Superior")
    @Secured({"ROLE_SUPERIOR"})
    @GetMapping
    public List<TipoTarefaResponseDTO> getAll() {
        return tipoTarefaService.getAll();
    }
}
