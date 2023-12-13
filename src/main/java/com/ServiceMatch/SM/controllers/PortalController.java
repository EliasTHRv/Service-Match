package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.entities.AppUser;
import com.ServiceMatch.SM.entities.Skill;
import com.ServiceMatch.SM.services.ServiceSkill;
import com.ServiceMatch.SM.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/") // http://localhost:8080/
public class PortalController {
    @Autowired
    ServiceSkill serviceSkill;

    @Autowired
    UserService serviceUser;
    @GetMapping("/")
    public String index(@RequestParam(name = "skill", required = false) String skill, ModelMap model)   {

        List<Skill> skills = serviceSkill.getSkills();
        model.addAttribute("skills", skills);

        if (skill != null) {
            List<AppUser> providers = serviceUser.loadUserBySkyll(skill);
            model.addAttribute("providers", providers);
            model.addAttribute("selectedSkill", skill);
        } else {
            // Asegúrate de agregar selectedSkill al modelo con un valor predeterminado
            List<AppUser> providers = serviceUser.loadUserBySkyll("Plomero");
            model.addAttribute("providers", providers);
            model.addAttribute("selectedSkill", "Plomero");// O cualquier valor predeterminado que desees
        }

        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o Contraseña inválidos");
        }
        return "login.html";
    }

    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

}
