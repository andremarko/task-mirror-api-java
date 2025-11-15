package task.mirror.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import task.mirror.api.dto.request.TarefaRequestDTO;
import task.mirror.api.dto.response.TarefaResponseDTO;
import task.mirror.api.dto.response.TarefaResumoLiderDTO;
import task.mirror.api.dto.response.TarefaResumoSubordinadoDTO;
import task.mirror.api.service.TarefaService;
import task.mirror.api.service.UsuarioService;

import java.security.Principal;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private UsuarioService usuarioService;


    // ========================= SUPERIOR =========================

    @Tag(name = "Superior")
    @Operation(summary = "Cria tarefa - Apenas para usuários com papel SUPERIOR (Líderes)")
    @Secured("ROLE_SUPERIOR")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarefaResponseDTO create(@RequestBody TarefaRequestDTO dto) {
        return tarefaService.create(dto);
    }

    @Tag(name = "Superior")
    @Operation(summary = "Atualiza uma tarefa - Apenas para usuários com papel SUPERIOR (Líderes)")
    @Secured("ROLE_SUPERIOR")
    @PutMapping("/{idTarefa}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long idTarefa, @RequestBody TarefaRequestDTO dto) {
        tarefaService.update(idTarefa, dto);
    }

    @Tag(name = "Superior")
    @Operation(summary = "Deleta uma tarefa - Apenas para usuários com papel SUPERIOR (Líderes)")
    @Secured("ROLE_SUPERIOR")
    @DeleteMapping("/{idTarefa}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long idTarefa) {
        tarefaService.delete(idTarefa);
    }

    @Tag(name = "Superior")
    @Operation(summary = "Retorna todas as tarefas da equipe- Apenas para usuários com papel SUPERIOR (Líderes)")
    @Secured("ROLE_SUPERIOR")
    @GetMapping
    public Page<TarefaResumoLiderDTO> getAll(
            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable) {
        return tarefaService.getAll(pageable);
    }

    @Tags({
            @Tag(name = "Superior"),
            @Tag(name = "Subordinado")
    })
    @Operation(summary = "Retorna uma tarefa por ID - Apenas para usuários com papel SUPERIOR (Líderes) e SUBORDINADO")
    @Secured({"ROLE_SUPERIOR", "ROLE_SUBORDINADO"})
    @GetMapping("/{idTarefa}")
    public TarefaResponseDTO getById(@PathVariable Long idTarefa, Principal principal) {
        return tarefaService.getById(idTarefa, principal);
    }

    // ========================= SUBORDINADO =========================

    @Tag(name = "Subordinado")
    @Operation(summary = "Concluir tarefa - Apenas para usuários com papel SUBORDINADO - gera automaticamente um feedback da tarefa concluída. Altera o status da tarefa para CONCLUIDA.")
    @Secured("ROLE_SUBORDINADO")
    @PutMapping("/{idTarefa}/concluir")
    public TarefaResponseDTO concluirTarefa(@PathVariable Long idTarefa) {
        return tarefaService.concluirTarefa(idTarefa);
    }

    @Tag(name = "Subordinado")
    @Operation(summary = "Retorna as tarefas do usuário logado - Apenas para usuários com papel SUBORDINADO")
    // PERFIL DO SUBORDINADO COM SUAS TAREFAS
    @Secured("ROLE_SUBORDINADO")
    @GetMapping("/meu-perfil")
    public Page<TarefaResumoSubordinadoDTO> getMinhasTarefas(
            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable, Principal principal) {
        Long idUsuario = usuarioService.getIdByUsername(principal.getName());
        return tarefaService.getTarefasDoUsuario(idUsuario, pageable);
    }
}
