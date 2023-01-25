package co.com.orbitec.cliente.servicio;

import co.com.orbitec.cliente.dao.RepositorioUsuarioDAO;
import co.com.orbitec.cliente.domain.Persona;
import co.com.orbitec.cliente.domain.Rol;
import co.com.orbitec.cliente.domain.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")

@Slf4j
public class UsuarioService implements UserDetailsService {
    @Autowired
    private RepositorioUsuarioDAO  repositorioUsuarioDAO;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Usuario usuario = repositorioUsuarioDAO.findByUsername(username);
       if(usuario==null){
         throw new UsernameNotFoundException(username);
       }
       var roles= new ArrayList<GrantedAuthority>(); //GrantedAuthority para envolver los roles
       for(Rol rol: usuario.getRoles()){

           roles.add(new SimpleGrantedAuthority(rol.getNombre())); //creamos un objeto de Simple..
       }
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
