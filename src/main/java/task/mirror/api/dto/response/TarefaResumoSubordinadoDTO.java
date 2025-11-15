package task.mirror.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaResumoSubordinadoDTO {
    private Long idTarefa;
    private LiderResponseDTO lider; // quem atribuiu
    private TipoTarefaResponseDTO tipoTarefa;
    private StatusTarefaResponseDTO statusTarefa;
    private BigDecimal tempoEstimado; // para conclusao
    private BigDecimal tempoReal; // tempo total para conclusao
    private OffsetDateTime dataInicio; // data inicial da tarefa
    private OffsetDateTime dataFim; // data final da tarefa (data limite para conclusao)
}
