package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.entities.ClientUser;
import com.ServiceMatch.SM.entities.ProviderUser;
import com.ServiceMatch.SM.enums.RolEnum;
import com.ServiceMatch.SM.services.ClientService;
import com.ServiceMatch.SM.services.ProviderService;
import com.ServiceMatch.SM.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String processClientRegistration(@ModelAttribute("clientUser") ClientUser clientUser) {
        clientUser.setRolEnum(RolEnum.USUARIO);
        clientService.registrar(clientUser);
        return "registration";
    }

    @PostMapping("/registration/provider")
    public String processProviderRegistration(@ModelAttribute("providerUser") ProviderUser providerUser) {
        providerUser.setRolEnum(RolEnum.PROVEEDOR);
        providerService.registrar(providerUser);
        return "registration";
    }
}