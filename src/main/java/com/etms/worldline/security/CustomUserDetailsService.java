package com.etms.worldline.security;

import com.etms.worldline.Repository.UserRepository;
import com.etms.worldline.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.lang.Long;

@Service
public class CustomUserDetailsService implements UserDetailsService {
   @Autowired
    private UserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not Found"));
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id){
        User user=userRepo.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));

        return UserPrincipal.create(user);
    }

}
