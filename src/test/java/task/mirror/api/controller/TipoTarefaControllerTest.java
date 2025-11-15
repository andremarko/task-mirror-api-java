package task.mirror.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
TESTES EM ENDPOINT VIVO - INTEGRACAO
 */

@SpringBootTest
@AutoConfigureMockMvc
public class TipoTarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles="SUPERIOR")
    void getAllTipoTarefa() throws Exception {
        mockMvc.perform(get("/api/tipo-tarefa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
