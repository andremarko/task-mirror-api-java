package task.mirror.api.integracao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private Long gerarUsuario() throws Exception {
        String json = """
                {
                    "username": "user_%s",
                    "idLider": 1,
                    "roleUsuario": "ROLE_SUBORDINADO",
                    "funcao": "DEV",
                    "cargo": "JR",
                    "setor": "TI"
                }
                """.formatted(UUID.randomUUID().toString().substring(0, 6));

        var result = mockMvc.perform(
                        post("/api/usuarios/admin/criar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode res = mapper.readTree(result.getResponse().getContentAsString());
        return res.get("idUsuario").asLong();
    }

    // ==========================================
    // TESTES
    // ==========================================

    private static final Long ID_EXISTENTE = 3L; // usuario da migration

    @Test
    @Order(1)
    @WithMockUser(roles = "ADMIN")
    void deveBuscarUsuarioExistente() throws Exception {
        mockMvc.perform(get("/api/usuarios/geral/" + ID_EXISTENTE))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @WithMockUser(roles = "ADMIN", username = "admin")
    void deveListarUsuariosParaAdmin() throws Exception {
        mockMvc.perform(get("/api/usuarios/admin/todos-usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @WithMockUser(roles = "ADMIN")
    void deveCriarUsuarioNovo_rollback() throws Exception {
        Long id = gerarUsuario();
        Assertions.assertNotNull(id);
    }

    @Test
    @Order(4)
    @WithMockUser(roles = "ADMIN")
    void deveAtualizarUsuarioExistente() throws Exception {
        String json = """
                {
                    "username": "upd_%s",
                    "roleUsuario": "ROLE_SUBORDINADO",
                    "funcao": "DEV",
                    "cargo": "PL",
                    "setor": "TI",
                    "idLider": 1
                }
                """.formatted(UUID.randomUUID().toString().substring(0,4));

        mockMvc.perform(
                        put("/api/usuarios/admin/atualizar/" + ID_EXISTENTE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    @WithMockUser(roles = "ADMIN")
    void deveDesativarUsuarioExistente() throws Exception {
        mockMvc.perform(put("/api/usuarios/admin/desativar/" + ID_EXISTENTE))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @WithMockUser(roles = "ADMIN")
    void deveAtivarUsuarioExistente() throws Exception {
        mockMvc.perform(put("/api/usuarios/admin/ativar/" + ID_EXISTENTE))
                .andExpect(status().isOk());
    }
}
