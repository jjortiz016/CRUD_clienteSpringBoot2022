package co.com.orbitec.cliente.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
@Entity
@Data
@Table(name="usuario")
public class Usuario implements Serializable {
    private static final long SerialVersionUID=1l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idusuario")
    private Long idUsuario;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    //Realizar mapeo de uno a muchos
    @OneToMany
    @JoinColumn(name="id_usuario")
    private List<Rol> roles;
}
