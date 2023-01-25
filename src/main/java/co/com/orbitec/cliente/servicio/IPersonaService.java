package co.com.orbitec.cliente.servicio;
import co.com.orbitec.cliente.domain.Persona;
import java.util.List;

public interface IPersonaService {

    public List<Persona> listarPersonas();
    public void guardar(Persona persona);
    public void eliminar(Persona persona);
    public Persona encontrarPersona(Persona personas);

}
