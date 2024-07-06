package com.example.hng_stage2_backend.auth;


import com.example.hng_stage2_backend.auth.response.FailureAuthResponse;
import com.example.hng_stage2_backend.auth.response.SuccessAuthResponse;
import com.example.hng_stage2_backend.config.JwtService;
import com.example.hng_stage2_backend.config.MyUserDetailsService;
import com.example.hng_stage2_backend.user.entity.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;
    private final MyUserDetailsService myUserDetailsService;
    private final AuthenticationManager authenticationManager;



    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginForm loginForm) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginForm.email(),
                            loginForm.password()
                    )
            );
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(userDetails);

            SuccessAuthResponse successAuthResponse = getSuccessAuthResponse(userDetails, jwtToken);

            return ResponseEntity.ok(successAuthResponse);
        } catch (AuthenticationException e) {
            FailureAuthResponse failureAuthResponse = new FailureAuthResponse("Bad Request", "Authentication failed", 401);
            return ResponseEntity.badRequest().body(failureAuthResponse);
        }
    }

    private SuccessAuthResponse getSuccessAuthResponse(MyUserDetails userDetails, String jwtToken) {
        SuccessAuthResponse.Data.User user = new SuccessAuthResponse.Data.User(
                userDetails.getUserId(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getUsername(),
                userDetails.getPhone()
        );
        SuccessAuthResponse.Data data = new SuccessAuthResponse.Data(jwtToken, user);
        return new SuccessAuthResponse("success", "Login successful", data);
    }


}





