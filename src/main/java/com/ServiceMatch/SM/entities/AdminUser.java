package com.ServiceMatch.SM.entities;


import com.ServiceMatch.SM.enums.RolEnum;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class AdminUser extends AppUser {
    public AdminUser() {
        this.setRolEnum(RolEnum.ADMINISTRADOR);
    }
}