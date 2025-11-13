package task.mirror.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaRequestDTO {

    @NotNull(message = "É obrigatório atribuir essa tarefa a um subordinado")
    private Long idUsuario;
    @NotNull
    private Long idLider;
    @NotNull
    private Long idStatusTarefa;

    @Max(value = 500, message = "Máximo de 500 caracteres")
    private String descricao;

    @NotNull(message = "O tempo estimado é obrigatório")
    @Positive(message = "O tempo estimado deve ser positivo")
    @Digits(integer = 3, fraction = 2, message = "O tempo estimado deve ter no máximo 3 dígitos inteiros e 2 decimais")
    private Double tempoEstimado;

    // inicio da tarefa definido pelo lider
    @NotNull(message = "A data de início é obrigatória")
    private LocalDateTime dataInicio;

    // data fim definido pelo lider
    @NotNull(message = "A data de fim é obrigatória")
    private LocalDateTime dataFim;
}
