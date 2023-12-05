package com.ServiceMatch.SM.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ServiceMatch.SM.entities.AppUser;
import com.ServiceMatch.SM.entities.Job;
import com.ServiceMatch.SM.entities.Provider;
import com.ServiceMatch.SM.entities.Skill;
import com.ServiceMatch.SM.enums.JobStatusEnum;
import com.ServiceMatch.SM.enums.RolEnum;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.services.ServiceJob;
import com.ServiceMatch.SM.services.ServiceProvider;
import com.ServiceMatch.SM.services.ServiceSkill;
import com.ServiceMatch.SM.services.UserService;

@Controller
@RequestMapping("/job")
public class JobControler {

    @Autowired
    ServiceJob serviceJob;

    @Autowired
    ServiceSkill serviceSkill;

    @Autowired
    UserService userService;

    @Autowired
    ServiceProvider serviceProvider;

    @GetMapping("/crearJobPrueba") // http://localhost:8080/job/create
    public String createJob(Model model) {

        List<Skill> skills = serviceSkill.getSkills();
        List<AppUser> appUsers = userService.loadUserByRol(RolEnum.USUARIO);
        List<Provider> providers = serviceProvider.loadUserByRol(RolEnum.PROVEEDOR);

        model.addAttribute("skills", skills);
        model.addAttribute("appUsers", appUsers);
        model.addAttribute("providers", providers);

        return "create_job.html";
    }

    // <!--Double cost, String description, Long idSkill, Long idUser, Long
    // idProvider-->
    @PostMapping("/crearJobPrueba")
    public String pruebaJob(
            @RequestParam(required = false) Double cost,
            @RequestParam(required = false) String description,
            Long idSkill, Long idUser,
            @RequestParam(required = false) Long idProvider, Model model) {
        
        try {
            serviceJob.createJob(cost, description, idSkill, idUser, idProvider);
            model.addAttribute("exito", "El job fue creado correctamente!");

        } catch (MyException ex) {
            model.addAttribute("error", ex.getMessage());
        }

        return "create_job.html";
    }

    @GetMapping("/list/provider/{id}")
    public String jobListPrueba(@PathVariable Long id, Model model) {

        List<Job> jobs = serviceJob.listByIdProvider(id);

        model.addAttribute("jobs", jobs);

        return "job_list.html";

    }

    // AQUI COMIENZA PAULINA MÉTODO GET Y POST PARA MODIFICAR JOB
    @GetMapping("/modify/{id}") // http://localhost:8080/job/modify/id
    public String modifyJob(@PathVariable Long id, ModelMap model) {
        // Lógica para modificar el job en la base de datos
        model.put("job", serviceJob.getOne(id));
        return "job_modify.html";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id, String description, Double cost, ModelMap model) {
        try {
            serviceJob.modifyJob(id, description, cost);
            model.put("exito", "El job fue modificado correctamente!");
            return "redirect:/job/modify/" + id;
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/job/modify/" + id;
        }
    }
    // AQUI TERMINA PAULINA

    @PostMapping("/list/provider/{id}/accept/{idJob}")
    public String acceptJob(@PathVariable Long id, @PathVariable Long idJob, String status, ModelMap model) {
        try {
            serviceJob.updateJobStatus(idJob, JobStatusEnum.ACCEPTED);
            model.put("exito", "Job actualizado correctamente");
            return "redirect:/job/list/provider/{id}";
        } catch (MyException ex) {
            model.put("error", "Error al actualizar el job: " + ex.getMessage());
            return "forward:/job/list/provider/{id}"; // Reenvía a la misma vista con mensajes de error
        }
    }

    @PostMapping("/list/provider/{id}/refused/{idJob}")
    public String refusedJob(@PathVariable Long id, @PathVariable Long idJob, String status, ModelMap model) {
        try {
            serviceJob.updateJobStatus(idJob, JobStatusEnum.REFUSED);
            model.put("exito", "Job actualizado correctamente");
            return "redirect:/job/list/provider/{id}";
        } catch (MyException ex) {
            model.put("error", "Error al actualizar el job: " + ex.getMessage());
            return "forward:/job/list/provider/{id}"; // Reenvía a la misma vista con mensajes de error
        }
    }

    @PostMapping("/list/provider/{id}/end/{idJob}")
    public String endJob(@PathVariable Long id, @PathVariable Long idJob, String status, ModelMap model) {
        try {
            serviceJob.updateJobStatus(idJob, JobStatusEnum.END);
            model.put("exito", "Job actualizado correctamente");
            return "redirect:/job/list/provider/{id}";
        } catch (MyException ex) {
            model.put("error", "Error al actualizar el job: " + ex.getMessage());
            return "forward:/job/list/provider/{id}"; // Reenvía a la misma vista con mensajes de error
        }
    }

}
