package task.mirror.api.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.mirror.api.dto.request.UsuarioRequestDTO;
import task.mirror.api.dto.response.UsuarioResponseDTO;
import task.mirror.api.mapper.UsuarioMapper;
import task.mirror.api.model.Usuario;
import task.mirror.api.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // GERAL
    @Transactional(readOnly = true)
    public UsuarioResponseDTO getById(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Transactional(readOnly = true)
    public Long getIdByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return usuario.getIdUsuario();
    }

    // ADMIN
    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        Usuario lider = null;
        if (dto.getIdLider() != null) {
            lider = usuarioRepository.findById(dto.getIdLider())
                    .orElseThrow(() -> new EntityNotFoundException("Líder não encontrado"));
        }
        Usuario usuario = usuarioMapper.toEntity(dto);
        String password = generateRandomPassword(8);

        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setLider(lider);
        usuarioRepository.save(usuario);
        UsuarioResponseDTO response = usuarioMapper.toResponseDTO(usuario);
        response.setSenhaGerada(password);
        return response;
    }

    // ADMIN
    @Transactional
    public UsuarioResponseDTO update(Long idUsuario, UsuarioRequestDTO dto, boolean gerarNovaSenha) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (dto.getUsername() != null) usuario.setUsername(dto.getUsername());
        if (dto.getRoleUsuario() != null) usuario.setRoleUsuario(dto.getRoleUsuario());
        if (dto.getFuncao() != null) usuario.setFuncao(dto.getFuncao());
        if (dto.getCargo() != null) usuario.setCargo(dto.getCargo());
        if (dto.getSetor() != null) usuario.setSetor(dto.getSetor());

        if (dto.getIdLider() != null) {
            Usuario lider = usuarioRepository.findById(dto.getIdLider())
                    .orElseThrow(() -> new EntityNotFoundException("Líder não encontrado"));
            usuario.setLider(lider);
        }

        if (gerarNovaSenha) {
            String novaSenha = generateRandomPassword(8);
            usuario.setPassword(passwordEncoder.encode(novaSenha));
            UsuarioResponseDTO response = usuarioMapper.toResponseDTO(usuarioRepository.save(usuario));
            response.setSenhaGerada(novaSenha);
            return response;
        }

        return usuarioMapper.toResponseDTO(usuarioRepository.save(usuario));
    }

    // SUPERIOR
    // TELA DE CADASTRO DE TAREFA, TELA MINHA EQUIPE
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> getAllUsersExceptAdminLider(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findByAtivoTrueAndRoleUsuarioNotIn(
                List.of("ROLE_SUPERIOR", "ROLE_ADMIN"), pageable
        );
        return usuarios.map(usuarioMapper::toResponseDTO);
    }

    // ADMIN
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> getAllForAdmin(Pageable pageable, Principal principal) {
        String username = principal.getName();

        Long idUsuarioLogado = usuarioRepository
                .findByUsername(username)
                .orElseThrow()
                .getIdUsuario();

        Page<Usuario> usuarios = usuarioRepository.findAllByIdUsuarioNot(idUsuarioLogado, pageable);
        return usuarios.map(usuarioMapper::toResponseDTO);
    }

    // ADMIN
    @Transactional
    public UsuarioResponseDTO desativarUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        usuario.setAtivo(false);
        Usuario atualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponseDTO(atualizado);
    }

    // ADMIN
    @Transactional
    public UsuarioResponseDTO ativarUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        usuario.setAtivo(true);
        Usuario atualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(atualizado);
    }

    @Transactional(readOnly = true)
    public BigDecimal getProdutividadeUsuario(Long idUsuario) {
        return usuarioRepository.calcularProdutividadeUsuario(idUsuario);
    }

    // Gera senha aleatoria
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}
