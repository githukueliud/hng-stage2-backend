package com.example.hng_stage2_backend.config;


import com.example.hng_stage2_backend.user.entity.MyUserDetails;
import com.example.hng_stage2_backend.user.entity.User;
import com.example.hng_stage2_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new MyUserDetails(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findUserByEmail(username);
//
//        if (user.isPresent()) {
//            var myUser = user.get();
//            return org.springframework.security.core.userdetails.User.builder()
//                    .username(myUser.getEmail())
//                    .password(myUser.getPassword())
//                    .build();
//        } else {
//            throw new UsernameNotFoundException(username);
//        }
//
//    }
}
