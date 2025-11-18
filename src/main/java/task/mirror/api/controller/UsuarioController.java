package task.mirror.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import task.mirror.api.dto.request.UsuarioRequestDTO;
import task.mirror.api.dto.response.UsuarioResponseDTO;
import task.mirror.api.service.UsuarioService;

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
    public UsuarioResponseDTO getById(@PathVariable Long idUsuario) {
        return usuarioService.getById(idUsuario);
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
    public UsuarioResponseDTO update(
            @PathVariable Long idUsuario,
            @RequestBody UsuarioRequestDTO dto,
            @RequestParam(defaultValue = "false") boolean gerarNovaSenha
    ) {
        return usuarioService.update(idUsuario, dto, gerarNovaSenha);
    }

    @Tag(name = "Admin")
    @Operation(summary = "Retorna todos os usuários - Apenas para usuários com papel ADMIN")
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/todos-usuarios")
    public Page<UsuarioResponseDTO> getAllForAdmin(
            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable,
            Principal principal
            ) {
        return usuarioService.getAllForAdmin(pageable, principal);
    }

    @Tag(name = "Admin")
    @Operation(summary = "Ativa um usuário - Apenas para usuários com papel ADMIN")
    @Secured("ROLE_ADMIN")
    @PutMapping("/admin/ativar/{idUsuario}")
    public UsuarioResponseDTO ativarUsuario(@PathVariable Long idUsuario) {
        return usuarioService.ativarUsuario(idUsuario);
    }

    @Tag(name = "Admin")
    @Operation(summary = "Desativa um usuário - Apenas para usuários com papel ADMIN")
    @Secured("ROLE_ADMIN")
    @PutMapping("/admin/desativar/{idUsuario}")
    public UsuarioResponseDTO desativarUsuario(@PathVariable Long idUsuario) {
        return usuarioService.desativarUsuario(idUsuario);
    }

    // ======= SUPERIOR

    @Tag(name = "Superior")
    @Operation(summary = "Retorna todos os usuários exceto ADMIN e LIDER (página da equipe) - Apenas para usuários com papel SUPERIOR")
    @Secured("ROLE_SUPERIOR")
    @GetMapping("/superior/equipe")
    public Page<UsuarioResponseDTO> getAllUsersExceptAdminLider(
            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable) {
        return usuarioService.getAllUsersExceptAdminLider(pageable);
    }
}
