package task.mirror.api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_FEEDBACKS")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_feedback")
    private Long idFeedback;

    @Lob
    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    @Column(name = "data_gerado", nullable = false)
    private LocalDateTime dataGerado = LocalDateTime.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarefa", nullable = false, unique = true)
    private Tarefa tarefa;
}
