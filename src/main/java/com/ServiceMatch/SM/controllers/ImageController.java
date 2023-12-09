package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.entities.Image;
import com.ServiceMatch.SM.services.ServiceImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static java.nio.file.Files.readAllBytes;


@RestController
public class ImageController {

    @Autowired
    private ServiceImage imageService; // Suponiendo que tienes un servicio para manejar las imágenes

    @GetMapping("/mostrar-imagen/{id}")
    public ResponseEntity<byte[]> mostrarImagen(@PathVariable Long id) {
        final Image[] m = {new Image()};
        imageService.getProviderById(id).ifPresent(p -> m[0] = p.getImagen());
        if (m[0] == null) {
            try {
                ClassPathResource imageFile = new ClassPathResource("img/trabajador11.jpg");
                InputStream inputStream = imageFile.getInputStream();

                byte[] imageBytes = readAllBytes((Path) inputStream);

                return ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageBytes);
            } catch (IOException e) {
                // Handle exception if the default image loading fails
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        byte[] imagenBytes = m[0].getContenido();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagenBytes, headers, HttpStatus.OK);


    }
}
