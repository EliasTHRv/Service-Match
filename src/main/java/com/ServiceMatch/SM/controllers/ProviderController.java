package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.entities.AppUser;
import com.ServiceMatch.SM.entities.Provider;
import com.ServiceMatch.SM.entities.Skill;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @GetMapping
    public List<Provider> findAll() {
        return providerService.findAll();
    }

    @PostMapping("/save")
    public String saveUser(
            @RequestParam(required = false) MultipartFile archivo,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String password2,
            @RequestParam(required = false) Long whatsApp,
            @RequestParam(required = false) List<Skill> skills,
            @RequestParam String role,
            Model model) {
        //Proveedor
        try {
            providerService.registrar(archivo, name, email, password, password2, whatsApp, skills);
        } catch (MyException ex) {
            model.addAttribute("error", ex.getMessage());
            return "registerOLD.html";
        }
        model.addAttribute("message", "User '" + name + "' saved successfully");
        return "redirect:/user/list";
    }
//TODO AGAIN
//    @PostMapping("/modify/{id}")
//    public String modify(@PathVariable Long id, String name, boolean active, MultipartFile archivo, ModelMap model) {
//        Optional<Provider> esProvider = providerService.getProviderById(id);
//        try {
//            if (esProvider.isPresent()) {
//                providerService.modifyProvider(archivo, id, name);
//            }
//            providerService.update(id, name, active);
//            return "redirect:../list";
//
//        } catch (MyException ex) {
//            model.put("error", ex.getMessage());
//            return "provider_modify.html";
//        }
//
//    }
//FIXME
//    @GetMapping("/providers/{skill}")
//    public String userProviderList(@RequestParam String skill, ModelMap model, RedirectAttributes redirectAttributes) {
//        List<AppUser> providers = providerService.loadBySkill(skill);
//
//        model.addAttribute("providers", providers);
//        model.addAttribute("selectedSkill", skill);
//        redirectAttributes.addAttribute("skill", skill);
//
//        return "redirect:/user/providers";
//
//    }

}
