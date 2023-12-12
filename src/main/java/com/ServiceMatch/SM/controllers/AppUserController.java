package com.ServiceMatch.SM.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ServiceMatch.SM.entities.AppUser;
import com.ServiceMatch.SM.entities.Job;
import com.ServiceMatch.SM.entities.Provider;
import com.ServiceMatch.SM.entities.Skill;
import com.ServiceMatch.SM.enums.RolEnum;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.services.ServiceJob;
import com.ServiceMatch.SM.services.ServiceProvider;
import com.ServiceMatch.SM.services.ServiceSkill;
import com.ServiceMatch.SM.services.UserService;

@Controller
@RequestMapping("/user")
public class AppUserController {

    @Autowired
    ServiceSkill serviceSkill;
    @Autowired
    ServiceProvider serviceProvider;

    @Autowired
    UserService serviceUser;

    @Autowired
    ServiceJob serviceJob;

    @GetMapping("/list") // http://localhost:8080/user/list/id
    public String listUsers(Model model, @RequestParam(defaultValue = "0") int page) {
        // Muestra 10 usuarios por página
        // Se guarda la lista de usuarios en "users" en formato de página (10 por
        // página)
        Page<AppUser> users = serviceUser.getPageOfUsers(page, 10);
        AppUser p = new AppUser();
        // Se inyectan al modelo todos los usuarios "userList"
        model.addAttribute("userList", users.getContent());
        // Se agrega información de paginación al modelo
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        // Devuelve el nombre de la plantilla HTML a renderizar
        return "user_list.html";
    }

    @GetMapping("/modify/{id}") // http://localhost:8080/user/modify/id
    public String modifyUser(@PathVariable Long id, ModelMap model) {
        // Lógica para modificar la skill en la base de datos
        model.put("user", serviceUser.getOne(id));

        return "user_modify.html";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id, String name, boolean active, MultipartFile archivo, ModelMap model) {
        Optional<Provider> esProvider = serviceProvider.getProviderById(id);
        try {
            if (esProvider.isPresent()) {
                serviceProvider.modifyProvider(archivo, id, name);
            }
            serviceUser.updateUser(id, name, active);
            return "redirect:../list";

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "user_modify.html";
        }

    }

    @GetMapping("/restore/{id}") // http://localhost:8080/user/restore/id
    public String restoreSkill(@PathVariable Long id, boolean active) {
        try {
            active = true;
            serviceUser.restoreUser(id, active);

            return "redirect:../list";

        } catch (Exception e) {
            return "redirect:../list";

        }
    }

    @GetMapping("/delete/{id}") // http://localhost:8080/user/delete/id
    public String deleteSkill(@PathVariable Long id, ModelMap model) {
        // Lógica para eliminar la skill en la base
        serviceUser.deleteUser(id);
        return "redirect:../list";
    }

    @GetMapping("/registration") // http://localhost:8080/user/registration
    public String showUserRegistrationForm(Model model) {
        List<Skill> skills = serviceSkill.getSkills();
        model.addAttribute("skillsRegistro", skills);
        return "register.html";
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
        try {
            if(role.equals("client")){
                serviceUser.registrar(name,email,password,password2);
                return "redirect:/user/list";
            }
            serviceProvider.registrar(archivo, name, email, password, password2, whatsApp, skills);
            model.addAttribute("message", "User '" + name + "' saved successfully");
        } catch (MyException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register.html";
        }
        return "redirect:/user/list";
    }

    @GetMapping("/providers")
    public String providerList(@RequestParam(name = "skill", required = false) String skill, ModelMap model) {
        List<Skill> skills = serviceSkill.getSkills();
        model.addAttribute("skills", skills);

        if (skill != null) {
            List<AppUser> providers = serviceUser.loadUserBySkyll(skill);
            model.addAttribute("providers", providers);
            model.addAttribute("selectedSkill", skill);

        } else {
            // Asegúrate de agregar selectedSkill al modelo con un valor predeterminado
            model.addAttribute("selectedSkill", ""); // O cualquier valor predeterminado que desees
        }

        return "provider_list.html";
    }

    @GetMapping("/providers/{skill}")
    public String userProviderList(@RequestParam String skill, ModelMap model, RedirectAttributes redirectAttributes) {
        List<AppUser> providers = serviceUser.loadUserBySkyll(skill);
        model.addAttribute("providers", providers);

        model.addAttribute("selectedSkill", skill);

        redirectAttributes.addAttribute("skill", skill);

        return "redirect:/user/providers";

    }

    @GetMapping("providers/details/{id}")
    public String providerDetails(@PathVariable Long id, ModelMap model) {
        try {
            List<Job> jobs = serviceJob.listJobByProvider(id);
            AppUser provider = serviceUser.getOne(id);

            double totalCalification = 0.0;

            for (Job job : jobs) {
                Long callification = job.getCallification();
                if (callification != null) {
                    totalCalification += callification;
                }
            }
            double averageCalification = jobs.isEmpty() ? 0.0 : totalCalification / jobs.size();

            model.addAttribute("provider", provider);
            model.addAttribute("providerName", jobs.isEmpty() ? "" : jobs.get(0).getProvider().getName());
            model.addAttribute("comments", jobs.stream().map(Job::getComment).collect(Collectors.toList()));
            model.addAttribute("averageCalification", averageCalification);

            return "provider_details";
        } catch (EntityNotFoundException e) {
            // Manejar la excepción cuando el proveedor no se encuentra
            model.addAttribute("error", "Proveedor no encontrado");
            return "redirect:/user/providers"; // Puedes crear una plantilla de error personalizada
        }
    }

    // MÉTODO PARA DEVOLVER VISTA EDITAR PERFIL TANTO PARA CLIENTE COMO PARA
    // PROVEEDOR
    @GetMapping("/editprofile/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        try {
            AppUser user = serviceUser.getOne(id);
            if (user.getRol().equals(RolEnum.USUARIO)) {
                return "client_profile.html";
            }
            if (user.getRol().equals(RolEnum.PROVEEDOR)) {
                return "provider_profile.html";
            } else {
                return "index.html";
            }

        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "index.html";
        }
    }

    // MÉTODO PARA EDITAR PERFIL CLIENTE
    @PostMapping("/client/editprofile/{id}")
    public String clientProfile(@PathVariable Long id, @RequestParam String name, @RequestParam String password,
            @RequestParam String password2, Model model) {
        try {
            serviceUser.editClient(id, name, password, password2);
            model.addAttribute("message", "User '" + name + "' edit successfully");
        } catch (MyException ex) {
            model.addAttribute("error", ex.getMessage());
            return "index.html";
        }
        return "redirect:/";
    }

    // METODO DE PRUEBA
    // @GetMapping("/provider/{id}")
    // public String userProvider(@RequestParam Long id, ModelMap model) {
    // AppUser provider = serviceUser.getOne(id);
    // model.addAttribute("provider", provider);
    // return "provider_vistaprueba.html";
    // }

}
