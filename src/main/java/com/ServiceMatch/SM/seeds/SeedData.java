package com.ServiceMatch.SM.seeds;

import com.ServiceMatch.SM.entities.*;
import com.ServiceMatch.SM.repository.SkillRepository;
import com.ServiceMatch.SM.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.ServiceMatch.SM.enums.RolEnum;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;

@Component
public class SeedData implements ApplicationRunner {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public SeedData(UserRepository userRepository, PasswordEncoder passwordEncoder, SkillRepository skillRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        // Verificar si ya existen usuarios en la base de datos

        if (userRepository.count() == 0) {
            // Si no hay usuarios, crear el admin

            Skill skill = new Skill();
            skill.setName("Plomero");
            skill.setActive(true);
            ArrayList<Skill> skills = new ArrayList<>();
            skills.add(skill);
            skillRepository.save(skill);

            // Encripta usando BCryptPasswordEncoder
            String encryptedPassword = passwordEncoder.encode("12341234");
            AdminUser adminUser = new AdminUser();
            adminUser.setRegistrationDate(new Date());
            adminUser.setName("Admin");
            adminUser.setActive(true);
            adminUser.setPassword(encryptedPassword);
            adminUser.setEmail("admin@gmail.com");
            userRepository.save(adminUser);

            ProviderUser provider = new ProviderUser();
            provider.setRegistrationDate(new Date());
            provider.setName("DMDM");
            provider.setActive(true);
            provider.setPassword(encryptedPassword);
            provider.setEmail("saurionx2005@gmail.com");
            provider.setWhatsApp(1234567890L);
            //FIXME
            //provider.setSkills(skills);
            userRepository.save(provider);

//
//            ProviderUser provider2 = new ProviderUser();
//            provider2.setRol(RolEnum.PROVEEDOR.toString());
//            provider2.setRegistrationDate(new Date());
//            provider2.setName("Provider2");
//            provider2.setActive(true);
//            provider2.setPassword(encryptedPassword);
//            provider2.setEmail("Mail2@gmail.com");
//            provider2.setWhatsApp(1234567890L);
//            //FIXME
//            //provider2.setSkills(skills);
//            userRepository.save(provider2);

            ClientUser adminUser2 = new ClientUser();
            adminUser2.setRol(RolEnum.USUARIO.toString());
            adminUser2.setRegistrationDate(new Date());
            adminUser2.setName("User1");
            adminUser2.setActive(true);
            adminUser2.setPassword(encryptedPassword);
            adminUser2.setEmail("emailadm2@afdmin.com");
            userRepository.save(adminUser2);

        }

    }

}