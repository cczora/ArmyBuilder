package com.cczora.armybuilder.service;

import com.cczora.armybuilder.config.exception.DuplicateUsernameException;
import com.cczora.armybuilder.models.entity.Account;
import com.cczora.armybuilder.data.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> user = users.findById(username);
        
        if(user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), grantedAuthorities);
    }

    public Account add(Account user) throws DuplicateUsernameException {
        
        Account appUser = users.findByUsername(user.getUsername());
        
        if (appUser != null) { //already there
            throw new DuplicateUsernameException(String.format("User %s is already registered", user.getUsername()));
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user = users.save(user);
        return user;
    }
}
