package task.mirror.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoTarefaResponseDTO {
    private Long tipoTarefaId;
    private String nome;
}
