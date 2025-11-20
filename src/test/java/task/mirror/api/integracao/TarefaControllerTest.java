package task.mirror.api.integracao;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static Long tarefaId;

    // ========================= SUPERIOR =========================

    @Test
    @Order(1)
    @WithMockUser(roles = "SUPERIOR")
    void createTarefa() throws Exception {

        String tarefaJson = """
        {
            "idUsuario": 3,
            "idLider": 1,
            "idTipoTarefa": 2,
            "descricao": "Descricao Teste",
            "tempoEstimado": 5.0
        }
        """;

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/tarefas/superior/criar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(tarefaJson)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Object raw = JsonPath.read(response, "$.idTarefa");
        assertThat(raw).as("response should contain idTarefa").isNotNull();
        if (raw instanceof Number) {
            tarefaId = ((Number) raw).longValue();
        } else {
            tarefaId = Long.parseLong(raw.toString());
        }

        System.out.println("Tarefa criada com ID: " + tarefaId);
        assertThat(tarefaId).isGreaterThan(0);
    }

    @Test
    @Order(2)
    @WithMockUser(roles = "SUPERIOR")
    void updateTarefa() throws Exception {

        String tarefaJson = """
        {
            "idUsuario": 3,
            "idTipoTarefa": 2,
            "descricao": "Descricao Teste Atualizada",
            "tempoEstimado": 6.0
        }
        """;

        mockMvc.perform(
                        put("/api/tarefas/superior/atualizar/" + tarefaId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(tarefaJson)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    // ========================= GET =========================

    @Test
    @Order(3)
    @WithMockUser(roles="SUPERIOR")
    void getAllTarefas() throws Exception {

        mockMvc.perform(
                        get("/api/tarefas/superior/tarefas-equipe")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @WithMockUser(username="usuario-subordinado1", roles="SUBORDINADO")
    void getTarefaById() throws Exception {

        mockMvc.perform(
                        get("/api/tarefas/" + tarefaId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ========================= SUBORDINADO =========================

    @Test
    @Order(5)
    @WithMockUser(username="usuario-subordinado1", roles="SUBORDINADO")
    void getTarefasDoSubordinado() throws Exception {

        mockMvc.perform(
                        get("/api/tarefas/subordinado/tarefas")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @WithMockUser(username="usuario-subordinado1", roles="SUBORDINADO")
    void concluirTarefa() throws Exception {

        mockMvc.perform(
                        put("/api/tarefas/subordinado/concluir/" + tarefaId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feedback").exists())
                .andExpect(jsonPath("$.feedback.conteudo").exists());
    }

    // ========================= DELETE =========================

    @Test
    @Order(7)
    @WithMockUser(roles="SUPERIOR")
    void deleteTarefa() throws Exception {

        mockMvc.perform(
                        delete("/api/tarefas/superior/deletar/" + tarefaId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
