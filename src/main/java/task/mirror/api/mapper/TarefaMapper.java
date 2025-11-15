package task.mirror.api.mapper;

import org.mapstruct.Mapper;
import task.mirror.api.dto.response.TarefaResponseDTO;
import task.mirror.api.dto.response.TarefaResumoLiderDTO;
import task.mirror.api.dto.response.TarefaResumoSubordinadoDTO;
import task.mirror.api.model.Tarefa;

@Mapper(componentModel = "spring")
public interface TarefaMapper {
    TarefaResponseDTO toResponseDTO(Tarefa tarefa);
    TarefaResumoLiderDTO toResumoLiderDTO(Tarefa tarefa);
    TarefaResumoSubordinadoDTO toResumoSubordinadoDTO(Tarefa tarefa);
}