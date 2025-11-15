package task.mirror.api.controller;

import org.junit.jupiter.api.Test;
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
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // ========================= TODOS =========================

    @Test
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
    @WithMockUser(roles = "ADMIN")
    void getAllUsuariosForAdmin() throws Exception {
        // DEVE RETORNAR TODOS USUARIOS CADASTRADOS
        mockMvc.perform(get("/api/usuarios/admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void desativarUsuario() throws Exception {
        // DEVE DESATIVAR O USUARIO COM ID 4
        mockMvc.perform(put("/api/usuarios/4/desativar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
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
    mockMvc.perform(put("/api/usuarios/admin/9")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(usuarioJson))
            .andDo(print())
            .andExpect(status().isOk());
    }

    // ========================= SUPERIOR =========================

    @Test
    @WithMockUser(roles = "SUPERIOR")
    void getAllUsersExceptAdminLider() throws Exception {
        // DEVE RETORNAR TODOS USUARIOS EXCETO ADMIN E LIDER
        mockMvc.perform(get("/api/usuarios/lider")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
