package com.ServiceMatch.SM.entities;

import javax.persistence.*;

import com.ServiceMatch.SM.enums.RolEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Data
@DiscriminatorValue("PROVEEDOR")
public class ProviderUser extends AppUser {
    public ProviderUser() {
        this.setRolEnum(RolEnum.PROVEEDOR);
    }
    @Transient
    private MultipartFile imagenFile;

    @Lob
    @Column(name = "imagen", columnDefinition = "BLOB")
    private byte[] imagen;

    @Column(name = "whats_app", nullable = true)
    private Long whatsApp;

    public void setImagenFile(MultipartFile imagenFile) {
        this.imagenFile = imagenFile;
        try {
            if (imagenFile != null && !imagenFile.isEmpty()) {
                this.imagen = imagenFile.getBytes();
            }
        } catch (IOException e) {
            // Manejar la excepción apropiadamente
            e.printStackTrace();
        }
    }
   @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   @JoinTable(
           name = "provider_skill",
           joinColumns = @JoinColumn(name = "provider_id"),
           inverseJoinColumns = @JoinColumn(name = "skill_id"))

   @Override
   public String toString() {
       // Llamar al toString de la superclase y agregar los detalles específicos de Provider
       return "Provider{Skills=, superclassDetails='" + super.toString() + "'}";
   }

}
