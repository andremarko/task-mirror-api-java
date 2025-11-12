package task.mirror.api.mapper;

import org.mapstruct.Mapper;
import task.mirror.api.dto.response.StatusTarefaResponseDTO;
import task.mirror.api.model.StatusTarefa;

@Mapper(componentModel = "spring")
public interface StatusTarefaMapper {
    StatusTarefaResponseDTO toStatusTarefaResponseDTO(StatusTarefa statusTarefa);
}
