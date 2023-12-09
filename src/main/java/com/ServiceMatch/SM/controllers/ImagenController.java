
package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.entities.AppUser;
import com.ServiceMatch.SM.entities.Provider;
import com.ServiceMatch.SM.services.ServiceProvider;
import com.ServiceMatch.SM.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

   @Controller
@RequestMapping("/imagen")
public class ImagenController {
    
 

    
    @Autowired
    ServiceProvider serviceProvider;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario (@PathVariable Long id){
        Provider usuario = serviceProvider.getOne(id);
        
       byte[] imagen= usuario.getImagen().getContenido();
       
       HttpHeaders headers = new HttpHeaders();
       
       headers.setContentType(MediaType.IMAGE_JPEG);
       
        
        
       return new ResponseEntity<>(imagen,headers, HttpStatus.OK); 
    }
}
    

