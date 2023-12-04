
package com.ServiceMatch.SM.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;

   @Data
@Entity
public class Picture {
    
    
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id ;
    private String mime;
    private String name;
    
    @Lob  @Basic(fetch = FetchType.LAZY) // le informa a spring que este dato puede ser GRANDE
    private byte[] content;

    
    
}
