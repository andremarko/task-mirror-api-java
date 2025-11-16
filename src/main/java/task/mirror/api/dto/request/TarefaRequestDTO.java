package task.mirror.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaRequestDTO {

    @Schema(description = "Id do usuário associado a essa tarefa", example="3")
    @NotNull(message = "É obrigatório atribuir essa tarefa a um subordinado")
    private Long idUsuario;

    @Schema(description = "Id do lider que criou essa tarefa", example="1")
    private Long idLider;

    @Schema(description = "Id do tipo da tarefa (vide tipo_tarefa)", example="6")
    @NotNull
    private Long idTipoTarefa;

    @Schema(description = "Descrição sobre a tarefa", example="Documentação da API REST do projeto Task Mirror. Necessário documetar endpoints que atualmente estão sendo consumidos pelo front.")
    @Max(value = 500, message = "Máximo de 500 caracteres")
    private String descricao;


    @Schema(description = "Tempo estimado da tarefa em horas (exemplo: 2.5 - 2 horas e meia)", example="3")
    @NotNull(message = "O tempo estimado é obrigatório")
    @Positive(message = "O tempo estimado deve ser positivo")
    @Digits(integer = 3, fraction = 2, message = "O tempo estimado deve ter no máximo 3 dígitos inteiros e 2 decimais")
    private BigDecimal tempoEstimado;
}
