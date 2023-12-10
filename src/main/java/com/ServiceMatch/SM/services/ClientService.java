package com.ServiceMatch.SM.services;

import com.ServiceMatch.SM.entities.AppUser;
import com.ServiceMatch.SM.entities.ClientUser;
import com.ServiceMatch.SM.entities.Skill;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.repository.ClientRepository;
import com.ServiceMatch.SM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;


    public void registrar(ClientUser clientUser) throws MyException{
        validar(clientUser);
        clientUser.setPassword(new BCryptPasswordEncoder().encode(clientUser.getPassword()));
        clientRepository.save(clientUser);
    }

    private void validar(ClientUser cu) throws MyException{
        if (cu.getName() == null || cu.getName().isEmpty()) {
            throw new MyException("El nombre no puede ser nulo o estar vacio");
        }

        if (cu.getEmail() == null || cu.getEmail().isEmpty()) {
            throw new MyException("El email no puede ser nulo o estar vacio");
        }

        if (cu.getPassword() == null || cu.getPassword().isEmpty() || cu.getPassword().length() <= 5) {
            throw new MyException("La contraseña no puede estar vacia, y debe tener mas de 5 digitos");
        }

    }

    public ClientUser getOne(Long id){
        return clientRepository.findById(id).get();
    }


    public void save(ClientUser clientUser) throws MyException {
        validar(clientUser);
        //TODO
        //Update de propiedades que no se ven.
        clientRepository.save(clientUser);

    }
}
