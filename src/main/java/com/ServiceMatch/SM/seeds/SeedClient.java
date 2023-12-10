package com.ServiceMatch.SM.seeds;

import com.ServiceMatch.SM.entities.ClientUser;
import com.ServiceMatch.SM.enums.RolEnum;
import com.ServiceMatch.SM.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SeedClient implements ApplicationRunner {
    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public SeedClient(ClientRepository clientRepository,
                      PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(clientRepository.countByRol("USUARIO")>0){
            return;
        }
        String encryptedPassword = passwordEncoder.encode("12341234");
        ClientUser adminUser2 = new ClientUser();
        adminUser2.setRol(RolEnum.USUARIO.toString());
        adminUser2.setRegistrationDate(new Date());
        adminUser2.setName("User1");
        adminUser2.setActive(true);
        adminUser2.setPassword(encryptedPassword);
        adminUser2.setEmail("emailadm2@afdmin.com");
        clientRepository.save(adminUser2);


    }
}
