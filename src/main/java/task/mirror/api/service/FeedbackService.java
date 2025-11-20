package task.mirror.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import task.mirror.api.model.Feedback;
import task.mirror.api.model.Tarefa;
import task.mirror.api.repository.FeedbackRepository;

import java.time.LocalDateTime;

@Service
public class FeedbackService {

    private final WebClient webClient;
    private final FeedbackRepository feedbackRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public FeedbackService(
            FeedbackRepository feedbackRepository,
            @Value("${OLLAMA_API_KEY}") String apiKey
    ) {
        this.feedbackRepository = feedbackRepository;
        this.objectMapper = new ObjectMapper();
        this.webClient = WebClient.builder()
                .baseUrl("https://ollama.com/api")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Transactional
    public Feedback gerarFeedback(Tarefa tarefa) throws Exception {

        // Monta o JSON para enviar ao Ollama
        String jsonBody = String.format("""
            {
              "model": "gpt-oss:20b-cloud",
              "messages": [
                {
                  "role": "user",
                  "content": "Analise a tarefa '%s' e gere um feedback profissional e conciso. Use os dados abaixo: \\n- Tempo estimado: %.2f horas\\n- Tempo real: %.2f horas\\n- Descrição da tarefa: %s\\n\\nInstruções detalhadas para o feedback:\\n1. Inicie reconhecendo o desempenho ou esforço do responsável.\\n2. Compare o tempo estimado com o tempo real, fornecendo uma análise breve sobre produtividade, eficiência ou necessidade de ajustes na estimativa.\\n3. Comente de forma objetiva sobre a execução da tarefa com base na descrição, destacando um ponto forte ou uma oportunidade clara de melhoria.\\n4. Use linguagem técnica, precisa e motivadora, adequada a gestores ou especialistas.\\n5. Mantenha o feedback em 1 ou 2 frases, evitando generalizações ou redundâncias.\\n6. Opcional: sugira um pequeno ajuste ou recomendação prática para tarefas futuras, sem se alongar."
                }
              ],
              "stream": false
            }
            """,
                tarefa.getDescricao(),
                tarefa.getTempoEstimado(),
                tarefa.getTempoReal() != null ? tarefa.getTempoReal() : 0.0,
                tarefa.getDescricao() != null ? tarefa.getDescricao() : ""
        );

        // Chamada ao Ollama
        Mono<String> responseMono = webClient.post()
                .uri("/chat")
                .bodyValue(jsonBody)
                .retrieve()
                .bodyToMono(String.class);

        String conteudoResposta = responseMono.block();

        // Extrai apenas o texto do feedback do JSON retornado
        JsonNode root = objectMapper.readTree(conteudoResposta);
        String feedbackTexto = root.path("message").path("content").asText();

        // Salva no banco
        Feedback feedback = new Feedback();
        feedback.setTarefa(tarefa);
        feedback.setConteudo(feedbackTexto); // SOMENTE O TEXTO DO FEEDBACK
        feedback.setDataGerado(LocalDateTime.now());

        return feedbackRepository.save(feedback);
    }
}
