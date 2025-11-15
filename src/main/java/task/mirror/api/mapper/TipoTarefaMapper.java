package task.mirror.api.mapper;

import org.mapstruct.Mapper;
import task.mirror.api.dto.response.TipoTarefaResponseDTO;
import task.mirror.api.model.TipoTarefa;

@Mapper(componentModel = "spring")
public interface TipoTarefaMapper {
   TipoTarefaResponseDTO toResponseDTO(TipoTarefa tipoTarefa);
}
