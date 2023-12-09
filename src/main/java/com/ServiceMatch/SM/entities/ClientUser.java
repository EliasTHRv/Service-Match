package com.ServiceMatch.SM.entities;

import com.ServiceMatch.SM.enums.RolEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@DiscriminatorValue("USUARIO")
public class ClientUser extends AppUser {
    public ClientUser() {
        this.setRolEnum(RolEnum.USUARIO);
    }
}