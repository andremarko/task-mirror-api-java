package task.mirror.api.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import task.mirror.api.dto.response.TarefaResponseDTO;
import task.mirror.api.mapper.TarefaMapper;
import task.mirror.api.model.Feedback;
import task.mirror.api.model.StatusTarefa;
import task.mirror.api.model.Tarefa;
import task.mirror.api.repository.StatusTarefaRepository;
import task.mirror.api.repository.TarefaRepository;
import task.mirror.api.service.FeedbackService;
import task.mirror.api.service.TarefaService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private StatusTarefaRepository statusTarefaRepository;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private TarefaMapper tarefaMapper;

    @InjectMocks
    private TarefaService tarefaService;

    @Test
    void deveConcluirTarefaDentroDoPrazo() throws Exception {
        Long idTarefa = 1L;

        OffsetDateTime dataInicio = OffsetDateTime.now(ZoneOffset.ofHours(-3)).minusHours(1);
        BigDecimal tempoEstimado = new BigDecimal("2.00");

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa);
        tarefa.setDataInicio(dataInicio);
        tarefa.setTempoEstimado(tempoEstimado);

        StatusTarefa statusConcluida = new StatusTarefa();
        statusConcluida.setNome("CONCLUIDA");

        Feedback feedbackMock = new Feedback();
        feedbackMock.setConteudo("Feedback gerado");

        TarefaResponseDTO dtoMock = new TarefaResponseDTO();

        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(tarefa));
        when(statusTarefaRepository.findByNome("CONCLUIDA")).thenReturn(statusConcluida);
        when(feedbackService.gerarFeedback(any())).thenReturn(feedbackMock);
        when(tarefaRepository.save(any())).thenReturn(tarefa);
        when(tarefaMapper.toResponseDTO(tarefa)).thenReturn(dtoMock);

        TarefaResponseDTO resultado = tarefaService.concluirTarefa(idTarefa);

        assertNotNull(tarefa.getTempoReal());
        assertTrue(tarefa.getTempoReal().compareTo(tempoEstimado) <= 0);

        assertEquals(statusConcluida, tarefa.getStatusTarefa());
        assertNotNull(dtoMock.getFeedback());
        assertEquals("Feedback gerado", dtoMock.getFeedback().getConteudo());
    }

    @Test
    void deveConcluirTarefaAtrasada() throws Exception {
        Long idTarefa = 2L;

        OffsetDateTime dataInicio = OffsetDateTime.now(ZoneOffset.ofHours(-3)).minusHours(4);
        BigDecimal tempoEstimado = new BigDecimal("3.00");

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa);
        tarefa.setDataInicio(dataInicio);
        tarefa.setTempoEstimado(tempoEstimado);

        StatusTarefa statusAtrasada = new StatusTarefa();
        statusAtrasada.setNome("ATRASADA");

        TarefaResponseDTO dtoMock = new TarefaResponseDTO();

        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(tarefa));
        when(statusTarefaRepository.findByNome("ATRASADA")).thenReturn(statusAtrasada);
        when(feedbackService.gerarFeedback(any())).thenReturn(null);
        when(tarefaRepository.save(any())).thenReturn(tarefa);
        when(tarefaMapper.toResponseDTO(tarefa)).thenReturn(dtoMock);

        TarefaResponseDTO resultado = tarefaService.concluirTarefa(idTarefa);

        assertNotNull(tarefa.getTempoReal());
        assertTrue(tarefa.getTempoReal().compareTo(tempoEstimado) > 0);
        assertEquals(statusAtrasada, tarefa.getStatusTarefa());
        assertNull(dtoMock.getFeedback());
    }

    @Test
    void deveConcluirMesmoQuandoFeedbackServiceFalha() throws Exception {
        Long idTarefa = 3L;

        OffsetDateTime dataInicio = OffsetDateTime.now(ZoneOffset.ofHours(-3)).minusHours(1);
        BigDecimal tempoEstimado = new BigDecimal("2.00");

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa);
        tarefa.setDataInicio(dataInicio);
        tarefa.setTempoEstimado(tempoEstimado);

        StatusTarefa statusConcluida = new StatusTarefa();
        statusConcluida.setNome("CONCLUIDA");

        TarefaResponseDTO dtoMock = new TarefaResponseDTO();

        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(tarefa));
        when(statusTarefaRepository.findByNome("CONCLUIDA")).thenReturn(statusConcluida);
        when(feedbackService.gerarFeedback(any()))
                .thenThrow(new RuntimeException("IA indisponível"));
        when(tarefaRepository.save(any())).thenReturn(tarefa);
        when(tarefaMapper.toResponseDTO(tarefa)).thenReturn(dtoMock);

        TarefaResponseDTO resultado = tarefaService.concluirTarefa(idTarefa);

        assertNotNull(tarefa.getTempoReal());
        assertEquals(statusConcluida, tarefa.getStatusTarefa());
        assertNull(resultado.getFeedback());
    }

}
