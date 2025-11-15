package task.mirror.api.integracao;


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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/*
TESTES EM ENDPOINT VIVO
 */

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // ========================= SUPERIOR =========================

    @Test
    @Order(1)
    @WithMockUser(roles="SUPERIOR")
    void createTarefa() throws Exception {
        String tarefaJson = """
        {
          "idUsuario": 3,
          "idLider": 1,
          "idTipoTarefa": 2,
          "descricao": "Descricao Teste",
          "tempoEstimado": 5,
          "dataFim": "2025-11-23T23:59:59Z"
         }
        """;

        mockMvc.perform(post("/api/tarefas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tarefaJson))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    @WithMockUser(roles="SUPERIOR")
    void updateTarefa() throws Exception {
        String tarefaJson = """
        {
        "idUsuario": 3,
        "idTipoTarefa": 2,
        "descricao": "Descricao Teste Atualizada",
        "tempoEstimado": 6,
        "dataFim": "2025-11-23T23:59:59Z"
        }
        """;

        mockMvc.perform(put("/api/tarefas/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tarefaJson))
                .andExpect(status().isNoContent());
    }

    // ========================= GET =========================

    @Test
    @Order(3)
    @WithMockUser(roles="SUPERIOR")
    void getAllTarefas() throws Exception {
        mockMvc.perform(get("/api/tarefas")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    // ========================= SUBORDINADO =========================

    @Test
    @Order(4)
    @WithMockUser(username="sub01", roles="SUBORDINADO")
    void getTarefasDoSubordinado() throws Exception {
        mockMvc.perform(get("/api/tarefas/meu-perfil")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    @WithMockUser(username="sub01", roles="SUBORDINADO")
    void concluirTarefa() throws Exception {
        mockMvc.perform(put("/api/tarefas/6/concluir")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feedback").isNotEmpty())
                .andExpect(jsonPath("$.feedback.conteudo").exists());
    }


    @Test
    @Order(6)
    @WithMockUser(username="sub01", roles="SUBORDINADO")
    void getTarefaById() throws Exception {
        mockMvc.perform(get("/api/tarefas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ========================= DELETE =========================

    @Test
    @Order(7)
    // DELEÇÃO FÍSICA
    @WithMockUser(roles="SUPERIOR")
    void deleteTarefa() throws Exception {
        mockMvc.perform(delete("/api/tarefas/6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
