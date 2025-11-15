package task.mirror.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.mirror.api.model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Page<Tarefa> findByUsuarioIdUsuario(Long idUsuario, Pageable pageable);
}
