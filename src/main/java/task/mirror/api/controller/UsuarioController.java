package task.mirror.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import task.mirror.api.dto.request.UsuarioRequestDTO;
import task.mirror.api.dto.response.UsuarioResponseDTO;
import task.mirror.api.service.UsuarioService;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Tag(name = "Geral - Requer autenticação (qualquer role)")
    @Operation(summary = "Retorna os dados do usuário pelo ID - Geral")
    @Secured({"ROLE_ADMIN", "ROLE_SUPERIOR", "ROLE_SUBORDINADO"})
    @GetMapping("/geral/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(usuarioService.getById(idUsuario));
    }

    // ======= ADMIN
    @Tag(name = "Admin")
    @Operation(summary = "Cria um novo usuário - Apenas para usuários com papel ADMIN")
    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO create(@RequestBody UsuarioRequestDTO dto) {
        return usuarioService.create(dto);
    }

    @Tag(name = "Admin")
    @Operation(summary = "Atualiza um usuário - Apenas para usuários com papel ADMIN")
    @Secured("ROLE_ADMIN")
    @PutMapping("/admin/atualizar/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> update(
            @PathVariable Long idUsuario,
            @RequestBody UsuarioRequestDTO dto,
            @RequestParam(defaultValue = "false") boolean gerarNovaSenha
    ) {
        return ResponseEntity.ok(usuarioService.update(idUsuario, dto, gerarNovaSenha));
    }

    @Tag(name = "Admin")
    @Operation(summary = "Retorna todos os usuários - Apenas para usuários com papel ADMIN")
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/todos-usuarios")
    public ResponseEntity<Page<UsuarioResponseDTO>> getAllForAdmin(
            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable,
            Principal principal
            ) {
        return ResponseEntity.ok(usuarioService.getAllForAdmin(pageable, principal));
    }

    @Tag(name = "Admin")
    @Operation(summary = "Ativa um usuário - Apenas para usuários com papel ADMIN")
    @Secured("ROLE_ADMIN")
    @PutMapping("/admin/ativar/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> ativarUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(usuarioService.ativarUsuario(idUsuario));
    }

    @Tag(name = "Admin")
    @Operation(summary = "Desativa um usuário - Apenas para usuários com papel ADMIN")
    @Secured("ROLE_ADMIN")
    @PutMapping("/admin/desativar/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> desativarUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(usuarioService.desativarUsuario(idUsuario));
    }

    @Tag(name = "Admin")
    @Operation(
        summary = "Calcula a produtividade de um usuário subordinado",
        description = "Essa operação calcula a produtividade de um usuário subordinado, que é baseada na média do tempo estimado versus o tempo real das tarefas concluídas. A produtividade é expressa como uma porcentagem. " +
                "Este cálculo só é permitido para usuários com o papel ADMIN. O valor retornado será a produtividade média de todas as tarefas concluídas do usuário no sistema. Caso o usuário não tenha tarefas concluídas ou não tenha o papel adequado, a API retorna um erro.",
        tags = { "Admin" }
    )
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/produtividade/{idUsuario}")
    public ResponseEntity<BigDecimal> produtividadeUsuario(@PathVariable Long idUsuario) {
        try {
            BigDecimal produtividade = usuarioService.getProdutividadeUsuario(idUsuario);
            if (produtividade == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(produtividade);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // ======= SUPERIOR
    @Tag(name = "Superior")
    @Operation(summary = "Retorna todos os usuários exceto ADMIN e LIDER (página da equipe) - Apenas para usuários com papel SUPERIOR")
    @Secured("ROLE_SUPERIOR")
    @GetMapping("/superior/equipe")
    public ResponseEntity<Page<UsuarioResponseDTO>> getAllUsersExceptAdminLider(
            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable) {
        return ResponseEntity.ok(usuarioService.getAllUsersExceptAdminLider(pageable));
    }
}
