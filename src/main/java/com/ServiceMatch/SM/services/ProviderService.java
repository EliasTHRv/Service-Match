package com.ServiceMatch.SM.services;

import com.ServiceMatch.SM.entities.Image;
import com.ServiceMatch.SM.entities.ProviderUser;
import com.ServiceMatch.SM.entities.Skill;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.repository.ProviderRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ServiceImage serviceImage;

    @Transactional
    public void registrar(MultipartFile archivo, String name, String email, String password, String password2, Long whatsapp, List<Skill>skills)
            throws MyException {

        validar(name, email, password, password2, whatsapp, skills);

        ProviderUser providerUser = new ProviderUser();

        providerUser.setName(name);
        providerUser.setWhatsApp(whatsapp);

        providerUser.setEmail(email);

        providerUser.setPassword(new BCryptPasswordEncoder().encode(password));
       //TODO
      //  provider.setSkills(skills);

        providerUser.setRol("PROVEEDOR");
        Image imagen= serviceImage.guardarImagen(archivo);
        providerUser.setImagen(imagen);
        providerRepository.save(providerUser);
    }

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

    @Transactional
    public void modifyProvider(MultipartFile archivo, Long id, String name) throws MyException {
        Optional<ProviderUser> result = providerRepository.findById(id);
        ProviderUser providerUser = new ProviderUser();
        if (result.isPresent()) {
            providerUser = result.get();
            providerUser.setName(name);
            Long idImagen=null;
            if(providerUser.getImagen() !=null){
                idImagen= providerUser.getImagen().getId();
            }
            Image imagen=serviceImage.actualizar(archivo, idImagen);
            providerUser.setImagen(imagen);
            providerRepository.save(providerUser);
        }
    }


    private void validar(String name, String email, String password, String password2, Long whatsapp, List<Skill> skills)
            throws MyException {

        if (name == null || name.isEmpty()) {
            throw new MyException("El nombre no puede ser nulo o estar vacio");
        }

        if (email == null || email.isEmpty()) {
            throw new MyException("El email no puede ser nulo o estar vacio");

        }

        if (password == null || password.length() <= 5) {
            throw new MyException("La contraseña no puede estar vacia, y debe tener mas de 5 digitos");
        }

        if (!password2.equals(password)) {
            throw new MyException("Las contraseñas ingresadas no coinciden");

        }
        if (whatsapp == null) {
            throw new MyException("El WhatsApp no puede ser nulo.");
        }
        if (skills.isEmpty()) {
            throw new MyException("La lista Oficio no puede ser nula.");
        }
        
    }



    public List<ProviderUser> findAll() {
        return providerRepository.findAll();
    }

    public Optional<ProviderUser> findById(Long id) {
        return providerRepository.findById(id);
    }
//FIXME
//    public List<AppUser> loadBySkill(String skill) {
//        return providerRepository.findBySkill(skill);
//    }
//TODO
    public void registrar(ProviderUser user) {
    }
}






