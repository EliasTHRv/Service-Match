package com.ServiceMatch.SM.entities;

import com.ServiceMatch.SM.enums.RolEnum;
import javax.persistence.*;

@Entity
@DiscriminatorValue("USUARIO")
public class ClientUser extends AppUser {
    public ClientUser() {
        this.setRolEnum(RolEnum.USUARIO);
    }
}