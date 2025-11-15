package task.mirror.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_TAREFAS")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarefa")
    private Long idTarefa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_lider", nullable = false)
    private Usuario lider;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_tarefa", nullable = false)
    private TipoTarefa tipoTarefa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_status_tarefa", nullable = false)
    private StatusTarefa statusTarefa;

    @Column(length = 500)
    private String descricao;

    @Column(precision = 5, scale = 2)
    private BigDecimal tempoEstimado;

    @Column(precision = 5, scale = 2)
    private BigDecimal tempoReal;

    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
}
