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
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

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

        // ---------- ARRANGE ----------
        Long idTarefa = 1L;

        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.ofHours(-3));
        OffsetDateTime inicio = agora.minusMinutes(90);
        OffsetDateTime fimPrevisto = agora.plusMinutes(10);

        // tarefa mockada
        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa);
        tarefa.setDataInicio(inicio);
        tarefa.setDataFim(fimPrevisto);

        StatusTarefa statusConcluida = new StatusTarefa();
        statusConcluida.setNome("CONCLUIDA");

        Feedback feedbackMock = new Feedback();
        feedbackMock.setConteudo("Feedback gerado");
        feedbackMock.setDataGerado(LocalDateTime.now());

        Tarefa tarefaSalva = tarefa;

        TarefaResponseDTO dtoMock = new TarefaResponseDTO();

        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(tarefa));
        when(statusTarefaRepository.findByNome("CONCLUIDA")).thenReturn(statusConcluida);
        when(feedbackService.gerarFeedback(any())).thenReturn(feedbackMock);
        when(tarefaRepository.save(any())).thenReturn(tarefaSalva);
        when(tarefaMapper.toResponseDTO(tarefaSalva)).thenReturn(dtoMock);

        TarefaResponseDTO resultado = tarefaService.concluirTarefa(idTarefa);

        // ---------- ASSERT ----------
        assertEquals(new BigDecimal("1.50"), tarefa.getTempoReal());

        // deve ser CONCLUIDA
        assertEquals(statusConcluida, tarefa.getStatusTarefa());

        assertNotNull(dtoMock.getFeedback());
        assertEquals("Feedback gerado", dtoMock.getFeedback().getConteudo());

        verify(tarefaRepository).save(tarefa);
        verify(feedbackService).gerarFeedback(tarefa);
        verify(statusTarefaRepository).findByNome("CONCLUIDA");
        verify(tarefaMapper).toResponseDTO(tarefa);
    }

    @Test
    void deveConcluirTarefaAtrasada() throws Exception {

        Long idTarefa = 1L;

        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.ofHours(-3));
        OffsetDateTime inicio = agora.minusMinutes(120);
        OffsetDateTime fimPrevisto = agora.minusMinutes(10);

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa);
        tarefa.setDataInicio(inicio);
        tarefa.setDataFim(fimPrevisto);

        StatusTarefa statusAtrasada = new StatusTarefa();
        statusAtrasada.setNome("ATRASADA");

        TarefaResponseDTO dtoMock = new TarefaResponseDTO();

        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(tarefa));
        when(statusTarefaRepository.findByNome("ATRASADA")).thenReturn(statusAtrasada);
        when(feedbackService.gerarFeedback(any())).thenReturn(null);
        when(tarefaRepository.save(any())).thenReturn(tarefa);
        when(tarefaMapper.toResponseDTO(tarefa)).thenReturn(dtoMock);

        TarefaResponseDTO resultado = tarefaService.concluirTarefa(idTarefa);

        assertEquals(new BigDecimal("2.00"), tarefa.getTempoReal());
        assertEquals(statusAtrasada, tarefa.getStatusTarefa());
        assertNull(dtoMock.getFeedback());
    }

    @Test
    void deveConcluirMesmoQuandoFeedbackServiceFalha() throws Exception {

        Long idTarefa = 1L;

        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.ofHours(-3));
        OffsetDateTime inicio = agora.minusMinutes(30);
        OffsetDateTime fimPrevisto = agora.plusMinutes(20);

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa);
        tarefa.setDataInicio(inicio);
        tarefa.setDataFim(fimPrevisto);

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

        assertEquals(statusConcluida, tarefa.getStatusTarefa());
        assertNull(resultado.getFeedback());
    }


}
