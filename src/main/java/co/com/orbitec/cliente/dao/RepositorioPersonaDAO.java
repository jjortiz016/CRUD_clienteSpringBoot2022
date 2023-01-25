package co.com.orbitec.cliente.dao;

import co.com.orbitec.cliente.domain.Persona;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPersonaDAO extends CrudRepository<Persona, Long> {

}
