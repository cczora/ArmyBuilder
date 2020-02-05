package com.cczora.armybuilder.service;

import com.cczora.armybuilder.models.Account;
import data.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository users;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository users, BCryptPasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = users.findByUsername(username);
        
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    public Account add(Account user) {
        
        Account appUser = users.findByUsername(user.getUsername());
        
        if (appUser != null) {
            throw new UsernameNotFoundException(user.getUsername());
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user = users.save(user);
        return user;
    }
}
