package co.com.orbitec.cliente.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

    @Entity
    @Data
    @Table(name="rol")
    public class Rol implements Serializable{
        private static final long SerialVersionUID=1L;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="idrole")
        private Long idRol;
        @NotEmpty
        private String nombre;
    }


