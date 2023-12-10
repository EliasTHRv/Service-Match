package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.entities.ClientUser;
import com.ServiceMatch.SM.entities.Skill;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/modify/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        ClientUser clientUser = clientService.getOne(id);
        model.addAttribute("clientUser", clientUser);
        return "client_modify";
    }

    @PostMapping("/modify")
    public String guardarClienteEditado(@ModelAttribute("clientUser") ClientUser clientUser) {
        try {
            clientService.save(clientUser);
            return "redirect:/user/list";
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }

}

