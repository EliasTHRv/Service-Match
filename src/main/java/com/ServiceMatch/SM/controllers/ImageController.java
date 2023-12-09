package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.entities.Image;
import com.ServiceMatch.SM.entities.ProviderUser;
import com.ServiceMatch.SM.services.ImageService;
import com.ServiceMatch.SM.services.ProviderService;
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
import java.util.Optional;

import static java.nio.file.Files.readAllBytes;


@RestController
public class ImageController {

    @Autowired
    private ProviderService providerService;
    @GetMapping("/mostrar-imagen/{id}")
    public ResponseEntity<byte[]> mostrarImagen(@PathVariable Long id) {
        Optional<ProviderUser> providerOptional = providerService.getProviderById(id);
        if (providerOptional.isPresent()) {
            ProviderUser provider = providerOptional.get();
            byte[] imagen = provider.getImagen();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
