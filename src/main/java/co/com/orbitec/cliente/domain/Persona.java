package co.com.orbitec.cliente.domain;
import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name="persona")
public class Persona implements Serializable {
    private static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (length = 45)
    @NotEmpty   //indica que no sea cadena vacia
    private String nombre;

    @Column (length = 45)
   // @NotEmpty(message="apellido del producto es obligatorio")
    @NotEmpty
   private String apellido;

  //  @NotBlank(message="El email  es obligatorio")

    @Column (length = 45)
    @NotEmpty
    @Email
    private String email;

    //@NotBlank(message="El telefono es obligatorio")
    @Column (length = 45)
    private String telefono;

    @NotNull
    private Double saldo;

}
