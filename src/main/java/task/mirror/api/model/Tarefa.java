package task.mirror.api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TBL_TAREFAS")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarefa")
    private Long idTarefa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lider", nullable = false)
    private Usuario lider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_tarefa", nullable = false)
    private TipoTarefa tipoTarefa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status_tarefa", nullable = false)
    private StatusTarefa statusTarefa;

    @Column(length = 500)
    private String descricao;

    @Column(precision = 5, scale = 2)
    private Double tempoEstimado;

    @Column(precision = 5, scale = 2)
    private Double tempoReal;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}
