package co.com.orbitec.cliente.dao;

import co.com.orbitec.cliente.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuarioDAO extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);

}
