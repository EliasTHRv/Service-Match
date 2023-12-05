package com.ServiceMatch.SM.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.ServiceMatch.SM.entities.AppUser;
import com.ServiceMatch.SM.entities.Skill;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.services.ServiceProvider;
import com.ServiceMatch.SM.services.ServiceSkill;
import com.ServiceMatch.SM.services.UserService;

@Controller
@RequestMapping("/user")
public class AppUserController {

    @Autowired
    UserService serviceUser;
    @Autowired
    ServiceSkill serviceSkill;
    @Autowired
    ServiceProvider serviceProvider;

    @GetMapping("/list") // http://localhost:8080/user/list/id
    public String listUsers(Model model, @RequestParam(defaultValue = "0") int page) {
        // Muestra 10 usuarios por página
        // Se guarda la lista de usuarios en "users" en formato de página (10 por
        // página)
        Page<AppUser> users = serviceUser.getPageOfUsers(page, 10);
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
    public String modify(@PathVariable Long id, String name, boolean active, ModelMap model) {

        try {
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
            ;
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
    // Añadir el seteo de rol proveedor o cliente
    public String saveUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String password2,
            @RequestParam(required = false) Long whatsApp,
            @RequestParam(required = false) Set<Long> skills,
            @RequestParam String role,
            Model model) {
        try {
            if (name == null || name.isBlank() || email == null || email.isBlank() ||
                    password == null || password.isBlank() || password2 == null || password2.isBlank() ||
                    role == null || role.isBlank()) {
                throw new MyException("Todos los campos marcados con * son obligatorios.");
            }

            if (!password.equals(password2)) {
                throw new MyException("Las contraseñas no coinciden.");
            }

            if ("client".equals(role)) {
                whatsApp = 0L;
                serviceUser.registrar(name, email, password, password2, whatsApp);
            } else if ("provider".equals(role)) {
                List<Skill> listaSkills = new ArrayList<>();
                for (Long skillId : skills) {
                    listaSkills.add(serviceSkill.getOne(skillId));
                }
                System.out.println(listaSkills);
                serviceProvider.registrar(name, email, password, password2, whatsApp, listaSkills);
            } else {
                throw new MyException("Rol no válido: " + role);
            }
            model.addAttribute("message", "User '" + name + "' saved successfully");
        } catch (MyException ex) {
            // En caso de excepción (por ejemplo, validación fallida), agrega un mensaje de
            // error al modelo
            model.addAttribute("error", ex.getMessage());
            // Devuelve el nombre de la plantilla HTML para mostrar el formulario de
            // registro con el mensaje de error
            return "register.html";
        }
        // Redirige a la URL "/user/list" después de guardar exitosamente
        return "redirect:/user/list";
    }
}
