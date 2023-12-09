package com.ServiceMatch.SM.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.ServiceMatch.SM.entities.ClientUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ServiceMatch.SM.entities.AppUser;
import com.ServiceMatch.SM.enums.RolEnum;
import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.repository.UserRepository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public List<AppUser> getUsers() {
        List<AppUser> users = new ArrayList<>();
        users = userRepository.findAll();
        return users;
    }



    public Page<AppUser> getPageOfUsers(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }
//FIXME
//    @Transactional
//    public void modifyUser(Long id, String name, String password, String mail, Long whatsApp) throws MyException {
//        validar(name, mail, password, password);
//        Optional<AppUser> result = userRepository.findById(id);
//        AppUser user = new AppUser();
//        if (result.isPresent()) {
//            user = result.get();
//            user.setName(name);
//            user.setPassword(password);
//            user.setEmail(mail);
//            userRepository.save(user);
//        }
//    }


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


    public AppUser getOne(Long id) {
        return userRepository.findById(id).get();
    }


    public List<AppUser> loadUserByRol(RolEnum rol) {
        return userRepository.findByRol(rol);
    }
    @Transactional
    public void deleteUser(Long id) {
        Optional<AppUser> result = userRepository.findById(id);
        AppUser user = new AppUser();
        if (result.isPresent()) {
            user = result.get();
            userRepository.delete(user);
        }
    }

}
