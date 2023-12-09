package com.ServiceMatch.SM.entities;

import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Provider extends AppUser {
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
