package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.entities.AppUser;
import com.ServiceMatch.SM.entities.ClientUser;
import com.ServiceMatch.SM.entities.ProviderUser;
import com.ServiceMatch.SM.services.ClientService;
import com.ServiceMatch.SM.services.ProviderService;
import com.ServiceMatch.SM.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class AppUserController {
    @Autowired
    UserService userService;
      @Autowired
    ProviderService providerService;
    @Autowired
    ClientService clientService;

    @GetMapping("/list")
    public String listUsers(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<AppUser> users = userService.getPageOfUsers(page, 10);
        AppUser p = new AppUser();
        model.addAttribute("userList", users.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        return "user_list.html";
    }

    @GetMapping("/restore/{id}") // http://localhost:8080/user/restore/id
    public String restoreSkill(@PathVariable Long id) {
        try {
            userService.restoreUser(id, true);
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
        //model.put("user", userService.getOne(id));
        return "provider_vistaprueba.html";
    }
}

