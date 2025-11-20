package task.mirror.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import task.mirror.api.dto.request.TarefaRequestDTO;
import task.mirror.api.dto.response.TarefaResponseDTO;
import task.mirror.api.dto.response.TarefaResumoLiderDTO;
import task.mirror.api.dto.response.TarefaResumoSubordinadoDTO;
import task.mirror.api.service.TarefaService;
import task.mirror.api.service.UsuarioService;

import java.math.BigDecimal;
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
    @PostMapping("/superior/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public TarefaResponseDTO create(@RequestBody TarefaRequestDTO dto) {
        return tarefaService.create(dto);
    }

    @Tag(name = "Superior")
    @Operation(summary = "Atualiza uma tarefa - Apenas para usuários com papel SUPERIOR (Líderes)")
    @Secured("ROLE_SUPERIOR")
    @PutMapping("/superior/atualizar/{idTarefa}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long idTarefa, @RequestBody TarefaRequestDTO dto) {
        tarefaService.update(idTarefa, dto);
    }

    @Tag(name = "Superior")
    @Operation(summary = "Deleta uma tarefa - Apenas para usuários com papel SUPERIOR (Líderes)")
    @Secured("ROLE_SUPERIOR")
    @DeleteMapping("/superior/deletar/{idTarefa}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long idTarefa) {
        tarefaService.delete(idTarefa);
    }

    @Tag(name = "Superior")
    @Operation(summary = "Retorna todas as tarefas da equipe- Apenas para usuários com papel SUPERIOR (Líderes)")
    @Secured("ROLE_SUPERIOR")
    @GetMapping("/superior/tarefas-equipe")
    public ResponseEntity<Page<TarefaResumoLiderDTO>> getAll(
            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable) {
        return ResponseEntity.ok(tarefaService.getAll(pageable));
    }

    @Tags({
            @Tag(name = "Superior"),
            @Tag(name = "Subordinado")
    })
    @Operation(summary = "Retorna uma tarefa por ID - Apenas para usuários com papel SUPERIOR (Líderes) e SUBORDINADO")
    @Secured({"ROLE_SUPERIOR", "ROLE_SUBORDINADO"})
    @GetMapping("/{idTarefa}")
    public ResponseEntity<TarefaResponseDTO> getById(@PathVariable Long idTarefa, Principal principal) {
        return ResponseEntity.ok(tarefaService.getById(idTarefa, principal));
    }

    // ========================= SUBORDINADO =========================

    @Tag(name = "Subordinado")
    @Operation(summary = "Concluir tarefa - Apenas para usuários com papel SUBORDINADO - gera automaticamente um feedback da tarefa concluída. Altera o status da tarefa para CONCLUIDA.")
    @Secured("ROLE_SUBORDINADO")
    @PutMapping("/subordinado/concluir/{idTarefa}")
    public ResponseEntity<TarefaResponseDTO> concluirTarefa(@PathVariable Long idTarefa) {
        return ResponseEntity.ok(tarefaService.concluirTarefa(idTarefa));
    }

    @Tag(name = "Subordinado")
    @Operation(summary = "Retorna as tarefas do usuário logado - Apenas para usuários com papel SUBORDINADO")
    // PERFIL DO SUBORDINADO COM SUAS TAREFAS
    @Secured("ROLE_SUBORDINADO")
    @GetMapping("/subordinado/tarefas")
    public ResponseEntity<Page<TarefaResumoSubordinadoDTO>> getMinhasTarefas(
            @ParameterObject
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            Principal principal) {

        return ResponseEntity.ok(tarefaService.getTarefasDoUsuarioAutenticado(pageable, principal));
    }

    // ========================= ADMIN =========================
    // ================ RELATORIOS =========================

    @Tag(name = "Admin")
    @Operation(summary = "Retorna o tempo médio de conclusão de todas as tarefas - Apenas para usuários com papel ADMIN")
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/tempo-medio-conclusao")
    public ResponseEntity<BigDecimal> getTempoMedioConclusao() {
        BigDecimal tempoMedio = tarefaService.getTempoMedioConclusaoTotal();
        if (tempoMedio == null) {
            return ResponseEntity.ok(BigDecimal.ZERO);
        }
        return ResponseEntity.ok(tempoMedio);
    }

    @Tag(name = "Admin")
    @Operation(summary = "Retorna o número total de tarefas por status - Apenas para usuários com papel ADMIN")
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/total-tarefas-por-status")
    public ResponseEntity<String> getTotalTarefasPorStatus() {
        String jsonResult = tarefaService.getQuantidadeTarefasPorStatus();
        if (jsonResult != null && !jsonResult.isEmpty()) {
            return ResponseEntity.ok(jsonResult);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No data available");
        }
    }

    @Tags({
            @Tag(name = "Admin"),
            @Tag(name = "Superior")
    }
    )
    @Operation(summary = "Retorna o número total de tarefas no sistema - Apenas para usuários com papel ADMIN e SUPERIOR")
    @Secured({"ROLE_ADMIN", "ROLE_SUPERIOR"})
    @GetMapping("/total-tarefas")
    public ResponseEntity<Long> getTotalTarefas() {
        long totalTarefas = tarefaService.getTotalTarefas();
        return ResponseEntity.ok(totalTarefas);
    }
}
