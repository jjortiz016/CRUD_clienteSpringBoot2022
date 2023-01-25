package co.com.orbitec.cliente.servicio;
import co.com.orbitec.cliente.dao.RepositorioPersonaDAO;
import co.com.orbitec.cliente.domain.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class PersonaService implements IPersonaService{
    @Autowired
    private RepositorioPersonaDAO repositorioPersonaDAO;

    @Override
    @Transactional (readOnly = true)
    public List<Persona> listarPersonas() {
        return (List<Persona>) repositorioPersonaDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(Persona persona) {
        repositorioPersonaDAO.save(persona);
    }

    @Override
    @Transactional
    public void eliminar(Persona persona) {
        repositorioPersonaDAO.delete(persona);
    }

    @Override
    @Transactional (readOnly = true)
    public Persona encontrarPersona(Persona persona) {
        return repositorioPersonaDAO.findById(persona.getId()).orElse(null);
    }
}
