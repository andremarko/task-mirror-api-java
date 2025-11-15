package task.mirror.api.mapper;

import org.mapstruct.Mapper;
import task.mirror.api.dto.request.UsuarioRequestDTO;
import task.mirror.api.dto.response.UsuarioResponseDTO;
import task.mirror.api.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponseDTO toResponseDTO(Usuario usuario);
    Usuario toEntity(UsuarioRequestDTO dto);
}
