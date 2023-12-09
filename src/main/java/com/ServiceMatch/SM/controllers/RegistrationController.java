package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.entities.ClientUser;
import com.ServiceMatch.SM.entities.ProviderUser;
import com.ServiceMatch.SM.enums.RolEnum;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.services.ClientService;
import com.ServiceMatch.SM.services.ProviderService;
import com.ServiceMatch.SM.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class RegistrationController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProviderService providerService;


    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("clientUser", new ClientUser());
        model.addAttribute("providerUser", new ProviderUser());
        return "registration";
    }

    @PostMapping("/registration/client")
    public String processClientRegistration(@ModelAttribute("clientUser") ClientUser clientUser,
                                            Model model) {
        clientUser.setRolEnum(RolEnum.USUARIO);
        try {
            clientService.registrar(clientUser);
        } catch (MyException ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/error";
        }
        return "registration";
    }

    @PostMapping("/registration/provider")
    public String processProviderRegistration(@ModelAttribute("providerUser") ProviderUser providerUser) throws MyException{
        MultipartFile imagenFile = providerUser.getImagenFile();
        // Procesar el archivo solo si se ha proporcionado
        if (imagenFile != null && !imagenFile.isEmpty()) {
            providerUser.setImagenFile(imagenFile); // Asignar el archivo cargado a la entidad ProviderUser
        }

        providerUser.setRolEnum(RolEnum.PROVEEDOR);
        providerService.registrar(providerUser);
        return "registration";
    }
}