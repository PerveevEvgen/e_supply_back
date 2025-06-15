package ua.kpi.diploma.e_supply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ua.kpi.diploma.e_supply.dto.AuthDTO;
import ua.kpi.diploma.e_supply.dto.LogoutRequestDTO;
import ua.kpi.diploma.e_supply.dto.TokenRefreshRequestDTO;
import ua.kpi.diploma.e_supply.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO authDTO) {
        return authService.authenticate(authDTO);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestBody TokenRefreshRequestDTO tokenRefreshRequestDTO) {
        return authService.refreshAccessToken(tokenRefreshRequestDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDTO logoutRequestDTO) {
        authService.logout(logoutRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
