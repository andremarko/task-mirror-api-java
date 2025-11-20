package task.mirror.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task.mirror.api.model.Tarefa;

import java.math.BigDecimal;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Page<Tarefa> findByUsuarioUsername(String username, Pageable pageable);
    // retorna o tempo medio de conclusao de todas as tarefas concluídas
    @Query(value = "SELECT task_mirror_pkg.fn_tempo_medio_conclusao_total() FROM DUAL", nativeQuery = true)
    BigDecimal calcularTempoMedioConclusaoTotal();
    long count();
}
