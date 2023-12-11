package com.ServiceMatch.SM.services;

import com.ServiceMatch.SM.entities.ClientUser;
import com.ServiceMatch.SM.entities.ProviderUser;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.repository.ProviderRepository;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;
    public List<ProviderUser> getProvider() {
        return providerRepository.findAll();
    }
    public Optional<ProviderUser> getProviderById(Long id) {
        return providerRepository.findById(id);
    }
    @Transactional
    public void deleteProvider(Long id) {
        Optional<ProviderUser> result = providerRepository.findById(id);
        ProviderUser providerUser = new ProviderUser();
        if (result.isPresent()) {
            providerUser = result.get();
            providerRepository.delete(providerUser);
        }
    }

    public List<ProviderUser> findAll() {
        return providerRepository.findAll();
    }

    public ProviderUser getOne(Long id){
        return providerRepository.findById(id).get();
    }


    public void registrar(ProviderUser pu) throws MyException {
        validar(pu);
        pu.setPassword(new BCryptPasswordEncoder().encode(pu.getPassword()));
        providerRepository.save(pu);
    }
    private void validar(ProviderUser pu) throws MyException {
        if (pu.getName() == null || pu.getName().isEmpty()) {
            throw new MyException("El nombre no puede ser nulo o estar vacio");
        }

        if (pu.getEmail() == null || pu.getEmail().isEmpty()) {
            throw new MyException("El email no puede ser nulo o estar vacio");
        }

        if (pu.getPassword() == null || pu.getPassword().isEmpty() || pu.getPassword().length() <= 5) {
            throw new MyException("La contraseña no puede estar vacia, y debe tener mas de 5 digitos");
        }


    }
    public List<ProviderUser> loadBySkill(String skill) {
        return null;
    }

    public void update(Long id, String name, boolean active) throws  MyException {
       throw  new MyException("Implementar");
    }
    public void save(ProviderUser providerUser) throws MyException {
        validar(providerUser);
        //TODO
        //Update de propiedades que no se ven ejemplo password.
        providerRepository.save(providerUser);

    }

}






