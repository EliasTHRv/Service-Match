package com.ServiceMatch.SM.entities;

import javax.persistence.*;

import com.ServiceMatch.SM.enums.RolEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@DiscriminatorValue("PROVEEDOR")
public class ProviderUser extends AppUser {
    public ProviderUser() {
        this.setRolEnum(RolEnum.PROVEEDOR);
    }
    @OneToOne
    private Image imagen;
    @Column(name = "whats_app", nullable = true)
    private Long whatsApp;
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "provider_skill",
//            joinColumns = @JoinColumn(name = "provider_id"),
//            inverseJoinColumns = @JoinColumn(name = "skill_id"))
//
//    @Override
//    public String toString() {
//        // Llamar al toString de la superclase y agregar los detalles específicos de Provider
//        return "Provider{Skills=, superclassDetails='" + super.toString() + "'}";
//    }

}
