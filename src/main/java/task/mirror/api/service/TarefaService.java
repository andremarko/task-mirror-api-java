package task.mirror.api.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.mirror.api.dto.request.TarefaRequestDTO;
import task.mirror.api.dto.response.*;
import task.mirror.api.mapper.TarefaMapper;
import task.mirror.api.model.*;
import task.mirror.api.repository.StatusTarefaRepository;
import task.mirror.api.repository.TarefaRepository;
import task.mirror.api.repository.TipoTarefaRepository;
import task.mirror.api.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.sql.Clob;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import task.mirror.api.repository.FeedbackRepository;


@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaMapper tarefaMapper;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoTarefaRepository tipoTarefaRepository;

    @Autowired
    private StatusTarefaRepository statusTarefaRepository;

    // SUPERIOR
    @Transactional
    public TarefaResponseDTO create(TarefaRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Usuario lider = usuarioRepository.findById(dto.getIdLider())
                .orElseThrow(() -> new EntityNotFoundException("Líder não encontrado"));

        TipoTarefa tipo = tipoTarefaRepository.findById(dto.getIdTipoTarefa())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de tarefa não encontrado"));

        Tarefa tarefa = new Tarefa();
        tarefa.setUsuario(usuario);
        tarefa.setLider(lider);
        tarefa.setTipoTarefa(tipo);
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setTempoEstimado(dto.getTempoEstimado()); // em horas ex 2.5 (2 horas e meia)
        OffsetDateTime dataInicio = OffsetDateTime.now(ZoneOffset.ofHours(-3));
        tarefa.setDataInicio(dataInicio); // data de criação definida automaticamente
        tarefa.setStatusTarefa(statusTarefaRepository.findByNome("PENDENTE")); // STATUS DEFINIDO AUTOMATICAMENTE NA CRIAÇÃO DA TAREFA
        tarefa = tarefaRepository.save(tarefa);

        // cria tarefa relacionada a um usuario - tela "minha equipe"
        TarefaResponseDTO response = tarefaMapper.toResponseDTO(tarefa);

        // adiciona último feedback, se houver (geralmente null no create)
        Feedback ultimoFeedback = feedbackRepository.findTopByTarefaOrderByDataGeradoDesc(tarefa);
        if (ultimoFeedback != null) {
            response.setFeedback(new FeedbackResponseDTO(
                    ultimoFeedback.getConteudo(),
                    ultimoFeedback.getDataGerado()
            ));
        }

        return response;
    }

    // SUPERIOR - TELA DE TAREFA
    @Transactional
    public TarefaResponseDTO update(Long idTarefa, TarefaRequestDTO dto) {
        Tarefa tarefa = tarefaRepository.findById(idTarefa)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        tarefa.setDescricao(dto.getDescricao());
        tarefa.setTempoEstimado(dto.getTempoEstimado());

        if (dto.getIdTipoTarefa() != null) {
            TipoTarefa tipoTarefa = tipoTarefaRepository.findById(dto.getIdTipoTarefa())
                    .orElseThrow(() -> new EntityNotFoundException("Tipo de tarefa não encontrado"));
            tarefa.setTipoTarefa(tipoTarefa);
        }

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            tarefa.setUsuario(usuario);
        }

        Tarefa updated = tarefaRepository.save(tarefa);
        return tarefaMapper.toResponseDTO(updated);
    }

    // SUPERIOR - TELA MINHA EQUIPE
    @Transactional
    public void delete(Long idTarefa) {
        if (!tarefaRepository.existsById(idTarefa)) {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }
        tarefaRepository.deleteById(idTarefa);
    }

    // RETORNA TODAS TAREFAS
    // TELA MINHA EQUIPE - SEÇÃO TAREFAS DA EQUIPE
    @Transactional(readOnly = true)
    public Page<TarefaResumoLiderDTO> getAll(Pageable pageable) {
        Page<Tarefa> tarefas = tarefaRepository.findAll(pageable);
        return tarefas.map(tarefaMapper::toResumoLiderDTO);
    }

    @Transactional(readOnly = true)
    public TarefaResponseDTO getById(Long idTarefa, Principal principal) {
        Tarefa tarefa = tarefaRepository.findById(idTarefa)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        Usuario usuarioLogado = usuarioRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if ("ROLE_SUBORDINADO".equals(usuarioLogado.getRoleUsuario())
                && !tarefa.getUsuario().getIdUsuario().equals(usuarioLogado.getIdUsuario())) {
            throw new SecurityException("Acesso negado: tarefa não pertence ao usuário");
        }

        TarefaResponseDTO dto = tarefaMapper.toResponseDTO(tarefa);

        Feedback ultimoFeedback = feedbackRepository.findTopByTarefaOrderByDataGeradoDesc(tarefa);
        if (ultimoFeedback != null) {
            dto.setFeedback(new FeedbackResponseDTO(
                    ultimoFeedback.getConteudo(),
                    ultimoFeedback.getDataGerado()
            ));
        }

        return dto;
    }

    // SUBORDINADO - CONCLUIR TAREFA - "MINHAS TAREFAS"
    @Transactional
    public TarefaResponseDTO concluirTarefa(Long idTarefa) {
        Tarefa tarefa = tarefaRepository.findById(idTarefa)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.ofHours(-3));
        tarefa.setDataFim(agora);

        long minutos = Duration.between(tarefa.getDataInicio(), agora).toMinutes();
        BigDecimal tempoReal = BigDecimal.valueOf(minutos)
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        tarefa.setTempoReal(tempoReal);

        StatusTarefa status = tempoReal.compareTo(tarefa.getTempoEstimado()) > 0
                ? statusTarefaRepository.findByNome("ATRASADA")
                : statusTarefaRepository.findByNome("CONCLUIDA");
        tarefa.setStatusTarefa(status);

        tarefaRepository.save(tarefa);

        TarefaResponseDTO response = tarefaMapper.toResponseDTO(tarefa);

        try {
            Feedback feedback = feedbackService.gerarFeedback(tarefa);
            if (feedback != null) {
                response.setFeedback(new FeedbackResponseDTO(feedback.getConteudo(), feedback.getDataGerado()));
            }
        } catch (Exception e) {
            response.setFeedback(null);
        }

        return response;
    }

    // PERFIL DO USUARIO (SUBORDINADO) - LISTA TODAS TAREFAS ASSOCIADAS AO USUARIO LOGADO
    @Transactional(readOnly = true)
    public Page<TarefaResumoSubordinadoDTO> getTarefasDoUsuarioAutenticado(Pageable pageable, Principal principal) {
        String username = principal.getName();
        Page<Tarefa> tarefas = tarefaRepository.findByUsuarioUsername(username, pageable);
        return tarefas.map(tarefaMapper::toResumoSubordinadoDTO);
    }

    // ========================= ADMIN =========================
    // ======================== RELATÓRIOS =========================

    // tempo médio de conclusão de todas as tarefas concluídas no sistema
    @Transactional(readOnly = true)
    public BigDecimal getTempoMedioConclusaoTotal() {
        BigDecimal tempoMedio = tarefaRepository.calcularTempoMedioConclusaoTotal();
        return tempoMedio != null ? tempoMedio : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public String getQuantidadeTarefasPorStatus() {
        return statusTarefaRepository.qtdTarefasPorStatus();
    }

    @Transactional(readOnly = true)
    public long getTotalTarefas() {
        return tarefaRepository.count();
    }
}

