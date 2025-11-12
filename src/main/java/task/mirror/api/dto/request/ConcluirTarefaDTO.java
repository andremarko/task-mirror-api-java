package task.mirror.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConcluirTarefaDTO {
    @NotNull
    private Long idTarefa;
}
