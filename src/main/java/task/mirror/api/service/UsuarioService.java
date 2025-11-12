package task.mirror.api.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import task.mirror.api.dto.request.UsuarioRequestDTO;
import task.mirror.api.dto.response.UsuarioResponseDTO;
import task.mirror.api.mapper.UsuarioMapper;
import task.mirror.api.model.Usuario;
import task.mirror.api.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public UsuarioResponseDTO getById(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return usuarioMapper.toResponseDTO(usuario);
    }

    // create - cadastro de usuario por ADMIN e/ou SUPERIOR
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        Usuario lider = null;
        if (dto.getIdLider() != null) {
            lider = usuarioRepository.findById(dto.getIdLider())
                    .orElseThrow(() -> new EntityNotFoundException("Líder não encontrado"));
        }
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setLider(lider);
        usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuario);
    }

    // getAll lista todos os usuarios do sistema diferente do lider e admin
    public Page<UsuarioResponseDTO> getAllUsersExceptAdminLider(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findByRoleUsuarioNotIn(
                List.of("ROLE_SUPERIOR", "ROLE_ADMIN"), pageable
        );
        return usuarios.map(usuarioMapper::toResponseDTO);
    }

    // getAll lista todos os usuarios do sistema com exceção role_admin
    public Page<UsuarioResponseDTO> getAllUsersExceptAdmin(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findByRoleUsuarioNotIn(
                List.of("ROLE_ADMIN"), pageable
        );
        return usuarios.map(usuarioMapper::toResponseDTO);
    }





}
