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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // ========================= TODOS =========================

    @Test
    @Order(1)
    @WithMockUser(roles = {"ADMIN", "SUPERIOR", "SUBORDINADO"})
    void getUsuariosById() throws Exception {
        // DEVE RETORNAR LIDER - ID_USUARIO = 1
        mockMvc.perform(get("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ========================= ADMIN =========================

    @Test
    @Order(2)
    @WithMockUser(roles = "ADMIN")
    void getAllUsuariosForAdmin() throws Exception {
        // DEVE RETORNAR TODOS USUARIOS CADASTRADOS
        mockMvc.perform(get("/api/usuarios/admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @WithMockUser(roles="ADMIN")
    public void createUsuario() throws Exception {
        String usuarioJson = """
                {
                    "username": "developer",
                    "idLider": 1,
                    "roleUsuario": "ROLE_SUBORDINADO",
                    "funcao": "Desenvolvedor",
                    "cargo": "Junior",
                    "setor": "TI"
                }
                """;
        mockMvc.perform(post("/api/usuarios/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                        .andDo(print())
                        .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    @WithMockUser(roles="ADMIN")
    public void updateUsuario() throws Exception {
        String usuarioJson = """
        {
            "username": "developer_updated",
            "roleUsuario": "ROLE_SUPERIOR",
            "funcao": "Desenvolvedor",
            "cargo": "Pleno",
            "setor": "TI",
            "idLider": 1
        }
        """;
    mockMvc.perform(put("/api/usuarios/admin/8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(usuarioJson))
            .andDo(print())
            .andExpect(status().isOk());
    }

    // ========================= SUPERIOR =========================

    @Test
    @Order(5)
    @WithMockUser(roles = "SUPERIOR")
    void getAllUsersExceptAdminLider() throws Exception {
        // DEVE RETORNAR TODOS USUARIOS EXCETO ADMIN E LIDER
        mockMvc.perform(get("/api/usuarios/lider")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ========================= ADMIN =========================

    @Test
    @Order(6)
    @WithMockUser(roles = "ADMIN")
    public void desativarUsuario() throws Exception {
        // DEVE DESATIVAR O USUARIO COM ID 6
        mockMvc.perform(put("/api/usuarios/8/desativar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
