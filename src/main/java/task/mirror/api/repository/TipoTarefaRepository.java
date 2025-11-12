package task.mirror.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.mirror.api.model.TipoTarefa;

@Repository
public interface TipoTarefaRepository extends JpaRepository<TipoTarefa, Long> {
}
