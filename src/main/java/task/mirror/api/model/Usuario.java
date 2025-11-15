package task.mirror.api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@Table(name="TBL_USUARIOS", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario" )
    private Long idUsuario;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "role_usuario")
    private String roleUsuario;

    @Column(nullable = false, length = 100)
    private String funcao;

    @Column(nullable = false, length = 100)
    private String cargo;

    @Column(nullable = false, length = 100)
    private String setor;

    @Column(nullable = false, columnDefinition = "NUMBER(1) DEFAULT 1")
    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "id_lider")
    private Usuario lider;

    @OneToMany(mappedBy = "lider")
    private List<Usuario> subordinados;

    @OneToMany(mappedBy = "usuario")
    private List<Tarefa> tarefasComoResponsavel;

    @OneToMany(mappedBy = "lider")
    private List<Tarefa> tarefasComoLider;
}
