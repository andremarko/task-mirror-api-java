package task.mirror.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    @Schema(description = "Username", example="admin")
    private String username;
    @Schema(description = "Senha (nos usuários cadastrados nas migrations, todos definidos como senha123", example="senha123")
    private String password;
}
