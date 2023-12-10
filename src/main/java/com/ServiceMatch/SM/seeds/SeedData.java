package com.ServiceMatch.SM.seeds;

import com.ServiceMatch.SM.entities.*;
import com.ServiceMatch.SM.repository.ProviderRepository;
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
import java.util.List;

@Component
public class SeedData implements ApplicationRunner {

    @Autowired
    private final ProviderRepository providerRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final SkillRepository skillRepository;
    @Autowired
    private final UserRepository userRepository;

    public SeedData(PasswordEncoder passwordEncoder,
                    SkillRepository skillRepository,
                    ProviderRepository providerRepository,
                    UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.skillRepository = skillRepository;
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        if (userRepository.count() != 0) {
            return;
        }

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
        provider.setSkills(skills);
        providerRepository.save(provider);


        ProviderUser provider2 = new ProviderUser();
        provider2.setRol(RolEnum.PROVEEDOR.toString());
        provider2.setRegistrationDate(new Date());
        provider2.setName("Provider2");
        provider2.setActive(true);
        provider2.setPassword(encryptedPassword);
        provider2.setEmail("Mail2@gmail.com");
        provider2.setWhatsApp(1234567890L);
        provider2.setSkills(skills);
        userRepository.save(provider2);

    }

}