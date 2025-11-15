package task.mirror.api.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import task.mirror.api.dto.request.UsuarioRequestDTO;
import task.mirror.api.dto.response.UsuarioResponseDTO;
import task.mirror.api.mapper.UsuarioMapper;
import task.mirror.api.model.Usuario;
import task.mirror.api.repository.UsuarioRepository;
import task.mirror.api.service.UsuarioService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarUsuarioComLider() {

        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setUsername("subordinado");
        dto.setIdLider(10L);

        Usuario lider = new Usuario();
        lider.setIdUsuario(10L);

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername("subordinado");

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setUsername("subordinado");

        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(lider));
        when(usuarioMapper.toEntity(dto)).thenReturn(novoUsuario);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded123");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(novoUsuario);
        when(usuarioMapper.toResponseDTO(novoUsuario)).thenReturn(responseDTO);

        UsuarioResponseDTO resultado = usuarioService.create(dto);

        assertNotNull(resultado);
        assertEquals("subordinado", resultado.getUsername());
        assertNotNull(resultado.getSenhaGerada());

        verify(usuarioRepository).findById(10L);
        verify(usuarioMapper).toEntity(dto);
        verify(passwordEncoder).encode(anyString());
        verify(usuarioRepository).save(novoUsuario);
        verify(usuarioMapper).toResponseDTO(novoUsuario);
    }

    @Test
    void deveLancarErroQuandoLiderNaoExiste() {

        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setIdLider(99L);

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                jakarta.persistence.EntityNotFoundException.class,
                () -> usuarioService.create(dto)
        );

        verify(usuarioRepository).findById(99L);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveCriarUsuarioSemLider() {

        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setUsername("lider");
        dto.setIdLider(null);

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername("lider");

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setUsername("lider");

        when(usuarioMapper.toEntity(dto)).thenReturn(novoUsuario);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedXYZ");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(novoUsuario);
        when(usuarioMapper.toResponseDTO(novoUsuario)).thenReturn(responseDTO);

        UsuarioResponseDTO resultado = usuarioService.create(dto);

        assertEquals("lider", resultado.getUsername());
        assertNotNull(resultado.getSenhaGerada());

        verify(usuarioRepository, never()).findById(any());
        verify(usuarioMapper).toEntity(dto);
        verify(usuarioRepository).save(novoUsuario);
    }
}
