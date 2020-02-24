package com.cczora.armybuilder.service;

import com.cczora.armybuilder.config.exception.DuplicateUsernameException;
import com.cczora.armybuilder.data.UserRepository;
import com.cczora.armybuilder.models.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private UserRepository users;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository users, BCryptPasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        Optional<Account> user = users.findById(username);

        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Username %s not found", username));
        }
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        //wrap in a class extending UserDetails
        return new MyUserPrincipal(user.get(), grantedAuthorities);
    }

    public void add(Account user) throws DuplicateUsernameException {
        
        Optional<Account> appUser = users.findById(user.getUsername());

        if (appUser.isPresent()) {
            throw new DuplicateUsernameException(String.format("User %s is already registered", user.getUsername()));
        }

        user.setPassword(encoder.encode(user.getPassword()));
        users.save(user);
    }
}
