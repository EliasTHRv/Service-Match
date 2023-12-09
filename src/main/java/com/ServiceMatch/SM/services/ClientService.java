package com.ServiceMatch.SM.services;

import com.ServiceMatch.SM.entities.AppUser;
import com.ServiceMatch.SM.entities.ClientUser;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.repository.ClientRepository;
import com.ServiceMatch.SM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    private void validar(String name, String email, String password, String password2)
            throws MyException {

        if (name == null || name.isEmpty()) {
            throw new MyException("El nombre no puede ser nulo o estar vacio");
        }

        if (email == null || email.isEmpty()) {
            throw new MyException("El email no puede ser nulo o estar vacio");

        }

        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MyException("La contraseña no puede estar vacia, y debe tener mas de 5 digitos");
        }

        if (!password2.equals(password)) {
            throw new MyException("Las contraseñas ingresadas no coinciden");

        }

    }


    public void registrar(ClientUser clientUser) {
        clientUser.setPassword(new BCryptPasswordEncoder().encode(clientUser.getPassword()));
        clientRepository.save(clientUser);
    }
}
