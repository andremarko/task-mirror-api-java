package task.mirror.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="TBL_STATUS_TAREFA")
public class StatusTarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_status_tarefa")
    private Long idStatusTarefa;

    @Column(nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "statusTarefa")
    private List<Tarefa> tarefas;
}
