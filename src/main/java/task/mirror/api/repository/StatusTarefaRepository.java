package task.mirror.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import task.mirror.api.model.StatusTarefa;

@Repository
public interface StatusTarefaRepository extends JpaRepository<StatusTarefa, Long> {
    StatusTarefa findByNome(String nome);
    @Procedure("task_mirror_pkg.proc_qtd_tarefas_por_status")
    String qtdTarefasPorStatus();
}
