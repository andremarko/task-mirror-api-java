package task.mirror.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiderResponseDTO {
    private Long idUsuario;
    private String username;
    private String funcao;
}
