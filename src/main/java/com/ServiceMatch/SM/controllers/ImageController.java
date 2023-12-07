package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.entities.Image;
import com.ServiceMatch.SM.entities.Provider;
import com.ServiceMatch.SM.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService; // Suponiendo que tienes un servicio para manejar las imágenes

    @GetMapping("/mostrar-imagen/{id}")
    public ResponseEntity<byte[]> mostrarImagen(@PathVariable Long id) {
        final Image[] m = {new Image()};
        imageService.getProviderById(id).ifPresent(p -> m[0] = p.getImagen());
        if (m[0] == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] imagenBytes = m[0].getContenido();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagenBytes, headers, HttpStatus.OK);


    }
}

/*




            // Convertir la imagen a un array de bytes
            byte[] imagenBytes = imagen.getBytes();

            // Crear una respuesta con el contenido de la imagen y el tipo de contenido



        */


