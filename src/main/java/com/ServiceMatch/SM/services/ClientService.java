package com.ServiceMatch.SM.services;

import com.ServiceMatch.SM.entities.AppUser;
import com.ServiceMatch.SM.entities.ClientUser;
import com.ServiceMatch.SM.enums.RolEnum;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.repository.UserRepository;
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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void registrar(String name, String email, String password, String password2) throws MyException {

        validar(name, email, password, password2);

        AppUser appUser = new AppUser();

        appUser.setName(name);

        appUser.setEmail(email);

        appUser.setPassword(new BCryptPasswordEncoder().encode(password));

        appUser.setRol("USUARIO");

        userRepository.save(appUser);

    }


    @Transactional
    public void restoreUser(Long id, boolean active) throws MyException {

        Optional<AppUser> result = userRepository.findById(id);
        AppUser user = new AppUser();
        if (result.isPresent()) {
            user = result.get();
            user.setActive(active);
            userRepository.save(user);
        }
    }

    @Transactional
    public void modifyUser(Long id, String name, String password, String mail, Long whatsApp) throws MyException {
        validar(name, mail, password, password);
        Optional<AppUser> result = userRepository.findById(id);
        AppUser user = new AppUser();
        if (result.isPresent()) {
            user = result.get();
            user.setName(name);
            user.setPassword(password);
            user.setEmail(mail);
            userRepository.save(user);
        }
    }

    @Transactional
    public void updateUser(Long id, String name, boolean active) throws MyException {
        Optional<AppUser> result = userRepository.findById(id);
        AppUser user = new AppUser();
        if (result.isPresent()) {
            user = result.get();
            user.setName(name);
            user.setActive(active);
            userRepository.save(user);
        }
    }

    public Page<AppUser> getPageOfUsers(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByEmail(email);

        if (appUser != null) {
            List<GrantedAuthority> permissions = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + appUser.getRol().toString());

            permissions.add(p);
            return new User(appUser.getEmail(), appUser.getPassword(), permissions);

        } else {
            return null;
        }

    }

    public AppUser getOne(Long id) {
        return userRepository.findById(id).get();
    }

    public List<AppUser> loadUserByRol(RolEnum rol) {
        return userRepository.findByRol(rol);
    }
//FIXME
//    public List<AppUser> loadUserBySkyll(String skill) {
//        return userRepository.findProvidersBySkill(skill);
//    }

    //TODO
    public void registrar(ClientUser user) {
    }
}