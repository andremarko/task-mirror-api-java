package task.mirror.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task.mirror.api.model.Usuario;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.ativo = false WHERE u.idUsuario = :idUsuario")
    void desativarPorId(@Param("idUsuario") Long idUsuario);
    Page<Usuario> findByAtivoTrueAndRoleUsuarioNotIn(List<String> roles, Pageable pageable);
    Page<Usuario> findAllByIdUsuarioNot(Long idUsuario, Pageable pageable);
    Optional<Usuario> findByUsername(String username);
    @Query(value = "SELECT task_mirror_pkg.fn_produtividade_usuario(?1) FROM DUAL",
            nativeQuery = true)
    BigDecimal calcularProdutividadeUsuario(Long idUsuario);
}
