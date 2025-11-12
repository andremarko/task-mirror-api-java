package task.mirror.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.mirror.api.dto.request.TarefaRequestDTO;
import task.mirror.api.dto.response.TarefaResponseDTO;
import task.mirror.api.model.Usuario;
import task.mirror.api.repository.TarefaRepository;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TipoTarefaService tipoTarefaService;

    @Autowired
    private StatusTarefaService statusTarefaService;


    // @Autowired
    // private TarefaMapper tarefaMapper;

    // somente o chefe pode criar tarefas, @Secured no controller... verificando a role SE é ROLE_LIDER ou ROLE_DIRIGENTE, ROLE_SUPERIOR.. SUPERIOR É UMA BOA
    @Transactional
    public TarefaResponseDTO create(TarefaRequestDTO dto) {

        Usuario usuario = usuarioService.


    }















}
