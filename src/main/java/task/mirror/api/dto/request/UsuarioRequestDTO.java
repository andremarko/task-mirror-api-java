package task.mirror.api.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import task.mirror.api.model.Usuario;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDTO {

    @Schema(description = "Username - nome do usuário", example="tecnico1")
    @NotBlank(message = "Username não pode ser nulo")
    private String username;

    @Schema(description = "Líder/Superior do funcionário. Se nulo, esse membro não há líder ou é o líder", example="1")
    private Long idLider;

    @Schema(description = "Role do usuário, hierarquica. ROLE_ADMIN, ROLE_SUPERIOR e ROLE_SUBORDINADO", example="ROLE_SUBORDINADO")
    @NotBlank(message = "Papel do usuário não pode ser nulo")
    private String roleUsuario;

    @Schema(description = "Função do funcionário", example="Técnico de Suporte")
    @NotBlank(message = "Função do funcionário não pode ser nula")
    private String funcao;

    @Schema(description = "Cargo - nome formal da sua função", example="Técnico de Suporte I")
    @NotBlank(message = "Cargo no funcionário não pode ser nulo")
    private String cargo;

    @Schema(description = "Setor do funcionário", example="TI")
    @NotBlank(message = "Setor do funcionário não pode ser nulo")
    private String setor;
}
