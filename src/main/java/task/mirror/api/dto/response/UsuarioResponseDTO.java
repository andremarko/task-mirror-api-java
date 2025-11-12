package task.mirror.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private Long idUsuario;
    private String username;
    private String funcao;
    private String cargo;
    private String setor;
}
