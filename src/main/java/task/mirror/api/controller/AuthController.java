package task.mirror.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.mirror.api.dto.request.LoginRequestDTO;
import task.mirror.api.dto.response.LoginResponseDTO;
import task.mirror.api.security.jwt.JwtUtil;

import java.util.List;

@Tag(name="Autenticação")
@RestController
@RequestMapping("/login")
@Tag(
        name="Autenticação",
        description = """
        
        - **admin**
        - **lider**
        - **usuario-subordinado1**
        - **usuario-subordinado2**
        - **usuario-subordinado3**
        - **usuario-subordinado4**
        - **usuario-subordinado5**        
        
        Senha padrão: **senha123**
        """
)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user);

        List<String> roles = user.getAuthorities()
                .stream()
                .map(ga -> ga.getAuthority())
                .toList();

        return ResponseEntity.ok(
                new LoginResponseDTO(token, user.getUsername(), roles)
        );

}
}
