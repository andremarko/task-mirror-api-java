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
@Table(name="TBL_TIPO_TAREFA")
public class TipoTarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_tarefa")
    private Long idTipoTarefa;

    @Column(nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "tipoTarefa")
    private List<Tarefa> tarefas;
}
