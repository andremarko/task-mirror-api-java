package task.mirror.api.mapper;

import org.mapstruct.Mapper;
import task.mirror.api.dto.response.FeedbackResponseDTO;
import task.mirror.api.model.Feedback;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    FeedbackResponseDTO toResponseDTO(Feedback feedback);
}

