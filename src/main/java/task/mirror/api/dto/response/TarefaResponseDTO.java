package task.mirror.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaResponseDTO {
    private Long idTarefa;
    private LiderResponseDTO lider; // quem atribuiu
    private UsuarioResponseDTO usuario; // executor
    private TipoTarefaResponseDTO tipoTarefa;
    private StatusTarefaResponseDTO statusTarefa;
    private String descricao;
    private Double tempoEstimado;
    private Double tempoReal;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}
