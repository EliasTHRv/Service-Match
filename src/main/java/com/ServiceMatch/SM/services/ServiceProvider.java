package com.ServiceMatch.SM.services;

import com.ServiceMatch.SM.entities.Provider;
import com.ServiceMatch.SM.entities.Skill;
import com.ServiceMatch.SM.enums.RolEnum;
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

@Service
public class ServiceProvider implements UserDetailsService {

    @Autowired
    private ProviderRepository providerRepository;

    // agregar nuevas skills a provedor
    public void addSkill(long idProvider, long idSkill) {
        // get privider by id
        // get skill by id
        // add skill to provider array array
        // save
    }

    // agregar select todas las propiedades

    @Transactional
    public void registrar(String name, String email, String password, String password2, Long whatsapp, List<Skill>skills)
            throws MyException {

        validar(name, email, password, password2, whatsapp);

        Provider provider = new Provider();

        provider.setName(name);
        provider.setWhatsApp(whatsapp);

        provider.setEmail(email);

        provider.setPassword(new BCryptPasswordEncoder().encode(password));
        provider.setSkills(skills);

        provider.setRol(RolEnum.PROVEEDOR);

        providerRepository.save(provider);
    }

    public List<Provider> getProvider() {
        return providerRepository.findAll();
    }

    @Transactional
    public void deleteProvider(Long id) {
        Optional<Provider> result = providerRepository.findById(id);
        Provider provider = new Provider();
        if (result.isPresent()) {
            provider = result.get();
            providerRepository.delete(provider);
        }
    }

    @Transactional
    public void modifyProvider(Long id, String name, String password, String mail, Long whatsApp) {
        Optional<Provider> result = providerRepository.findById(id);
        Provider provider = new Provider();
        if (result.isPresent()) {
            provider = result.get();
            provider.setName(name);
            provider.setPassword(password);
            provider.setEmail(mail);
            provider.setWhatsApp(whatsApp);
            providerRepository.save(provider);
        }
    }

    public Page<Provider> getPageOfUsers(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return providerRepository.findAll(pageable);
    }

    private void validar(String name, String email, String password, String password2, Long whatsapp)
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
        if (whatsapp == null) {
            throw new MyException("El WhatsApp no puede ser nulo.");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Provider provider = providerRepository.findByName(name);

        if (provider != null) {
            List<GrantedAuthority> permissions = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE " + provider.getRol().toString());

            permissions.add(p);
            return new User(provider.getEmail(), provider.getPassword(), permissions);

        } else {
            return null;
        }

}
    
      public List<Provider> loadUserByRol(RolEnum rol) {
    return providerRepository.findByRol(rol);
}


}






