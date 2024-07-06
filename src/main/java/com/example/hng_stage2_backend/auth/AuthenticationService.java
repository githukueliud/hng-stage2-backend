package com.example.hng_stage2_backend.auth;


import com.example.hng_stage2_backend.config.JwtService;
import com.example.hng_stage2_backend.config.MyUserDetailsService;
import com.example.hng_stage2_backend.organization.service.OrganizationService;
import com.example.hng_stage2_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService myUserDetailsService;



    public AuthenticationResponse authenticate(AuthenticationRequest request) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found!"));
        var jwtToken = jwtService.generateToken((UserDetails) myUserDetailsService);
        return new AuthenticationResponse(jwtToken);
    }


}
