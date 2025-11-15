package task.mirror.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.mirror.api.dto.response.TipoTarefaResponseDTO;
import task.mirror.api.mapper.TipoTarefaMapper;
import task.mirror.api.model.TipoTarefa;
import task.mirror.api.repository.TipoTarefaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoTarefaService {

    @Autowired
    private TipoTarefaRepository tipoTarefaRepository;

    @Autowired
    private TipoTarefaMapper tipoTarefaMapper;

    // getAll somente
    // PLANEJAMENTO / ORGANIZACAO, EXECUCAO / OPERACAO, ANALISE / PESQUISA, SUPORTE / ATENDIMENTO, REVISAO / VERIFICACAO, DOCUMENTACAO, MANUTENCAO / AJUSTE, COMUNICACAO / REUNIOES, AUDITORIA / CONTROLE
    @Transactional(readOnly = true)
    public List<TipoTarefaResponseDTO> getAll() {
        List<TipoTarefa> tipoTarefa = tipoTarefaRepository.findAll();
        return tipoTarefa.stream().map(tipoTarefaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
