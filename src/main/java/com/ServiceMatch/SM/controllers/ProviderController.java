package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.entities.ClientUser;
import com.ServiceMatch.SM.entities.ProviderUser;
import com.ServiceMatch.SM.entities.Skill;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public List<ProviderUser> findAll() {
        return providerService.findAll();
    }

    @GetMapping("/modify/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        ProviderUser providerUser = providerService.getOne(id);
        model.addAttribute("providerUser", providerUser);
        return "provider_modify";
    }
    //FIX ME
    //ESTO TIENE QUE IR COMO OBJETO
   @PostMapping("/modify/{id}")
   public String modify(@PathVariable Long id, String name, boolean active, MultipartFile archivo, ModelMap model) {
       Optional<ProviderUser> esProvider = providerService.getProviderById(id);
       try {
           //FIXME aqui debe actualizar la imagen
//           if (esProvider.isPresent()) {
//               providerService.modify(archivo, id, name);
//           }
           providerService.update(id, name, active);
           return "redirect:../list";

       } catch (MyException ex) {
           model.put("error", ex.getMessage());
           return "provider_modifyOLD.html";
       }

   }

   @GetMapping("/providers/{skill}")
   public String userProviderList(@RequestParam String skill, ModelMap model, RedirectAttributes redirectAttributes) {
       List<ProviderUser> providers = providerService.loadBySkill(skill);

       model.addAttribute("providers", providers);
       model.addAttribute("selectedSkill", skill);
       redirectAttributes.addAttribute("skill", skill);

       return "redirect:/user/providers";

   }

    //Get Image by Provider ID
    @GetMapping(value = "/{providerId}/image")
    public ResponseEntity<byte[]> getProviderImage(@PathVariable Long providerId) {
        Optional<ProviderUser> productOptional = providerService.getProviderById(providerId);
        if (productOptional.isPresent()) {
            ProviderUser pu = productOptional.get();
            byte[] imageBytes = java.util.Base64.getDecoder().decode(pu.getImagen());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
        }
    }

}
