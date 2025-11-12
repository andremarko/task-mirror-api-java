package task.mirror.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.mirror.api.dto.response.StatusTarefaResponseDTO;
import task.mirror.api.mapper.StatusTarefaMapper;
import task.mirror.api.model.StatusTarefa;
import task.mirror.api.repository.StatusTarefaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusTarefaService {

    @Autowired
    private StatusTarefaRepository statusTarefaRepository;

    @Autowired
    private StatusTarefaMapper statusTarefaMapper;

    // getAll somente
    // CONCLUIDA, PENDENTE, EM_ANDAMENTO, CONCLUIDA, ATRASADA, CANCELADA
    // response dto
    @Transactional(readOnly = true)
    public List<StatusTarefaResponseDTO> getAll() {
        List<StatusTarefa> statusTarefa = statusTarefaRepository.findAll();
        return statusTarefa.stream().map(statusTarefaMapper::toStatusTarefaResponseDTO)
                .collect(Collectors.toList());
    }
}
