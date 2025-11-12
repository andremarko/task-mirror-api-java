package task.mirror.api.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import task.mirror.api.model.Usuario;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "Username não pode ser nulo")
    private String username;

    private Long idLider;

    @NotBlank(message = "Senha não pode ser nula")
    private String senha;

    @NotBlank(message = "Papel do usuário não pode ser nulo")
    private String roleUsuario;

    @NotBlank(message = "Função do funcionário não pode ser nula")
    private String funcao;

    @NotBlank(message = "Cargo no funcionário não pode ser nulo")
    private String cargo;

    @NotBlank(message = "Setor do funcionário não pode ser nulo")
    private String setor;
}
