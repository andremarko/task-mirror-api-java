package task.mirror.api.dto.response;

// para card de tarefa, resumo com id, titulo, status,

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaResumoLiderDTO {
    private Long idTarefa;
    private UsuarioResponseDTO usuario; // atribuido
    private StatusTarefaResponseDTO statusTarefa;
    private TipoTarefaResponseDTO tipoTarefa;
    private BigDecimal tempoEstimado;
    private BigDecimal tempoReal;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
}
