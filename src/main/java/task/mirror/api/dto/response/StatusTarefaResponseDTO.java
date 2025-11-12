package task.mirror.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusTarefaResponseDTO {
    private Long idStatusTarefa;
    private String nome;
}
