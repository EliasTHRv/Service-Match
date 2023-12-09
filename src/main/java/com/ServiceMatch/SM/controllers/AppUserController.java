package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.entities.AppUser;
import com.ServiceMatch.SM.entities.ClientUser;
import com.ServiceMatch.SM.entities.Provider;
import com.ServiceMatch.SM.entities.Skill;
import com.ServiceMatch.SM.services.ClientService;
import com.ServiceMatch.SM.services.ProviderService;
import com.ServiceMatch.SM.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//No distingue entre proveedor o cliente
@RestController
@RequestMapping("/user")
public class AppUserController {
    @Autowired
    UserService serviceUser;
    @Autowired
    ClientService clientService;
    @Autowired
    ProviderService providerService;


    @GetMapping("/list")
    public String listUsers(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<AppUser> users = serviceUser.getPageOfUsers(page, 10);
        AppUser p = new AppUser();
        model.addAttribute("userList", users.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        return "user_list.html";
    }

    @GetMapping("/restore/{id}") // http://localhost:8080/user/restore/id
    public String restoreSkill(@PathVariable Long id) {
        try {
            serviceUser.restoreUser(id, true);

            return "redirect:../list";

        } catch (Exception e) {
            return "redirect:../list";

        }
    }
//
//    @GetMapping("/registration") // http://localhost:8080/user/registration
//    public String showUserRegistrationForm(Model model) {
//        List<Skill> skills = serviceSkill.getSkills();
//        model.addAttribute("skillsRegistro", skills);
//        return "registerOLD.html";
//    }

    @GetMapping("/modify/{id}") // http://localhost:8080/user/modify/id
    public String modifyUser(@PathVariable Long id, ModelMap model) {
        model.put("user", serviceUser.getOne(id));
        return "provider_modify.html";
    }


    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new AppUser());
        return "register";
    }


    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute AppUser user) {
        if (user instanceof ClientUser) {
            clientService.registrar((ClientUser) user);
        } else if (user instanceof Provider) {
            providerService.registrar((Provider) user);
        } else {
            return "redirect:/error";
        }
        return "redirect:/inicio";
    }


}

