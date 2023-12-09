package com.ServiceMatch.SM.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.ServiceMatch.SM.enums.RolEnum;

import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rol", discriminatorType = DiscriminatorType.STRING, columnDefinition = "VARCHAR(255) DEFAULT 'USUARIO'")
@Data
@DiscriminatorValue("USUARIO")
public class AppUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false, insertable = false, updatable = false)
    private String rol;

    @Column(name = "registration_date", nullable = false)
    private Date registrationDate = new Date();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean active = true;

    public RolEnum getRolEnum() {
        return RolEnum.valueOf(this.rol);
    }

    public void setRolEnum(RolEnum rol) {
        this.rol = rol.name();
    }
}
