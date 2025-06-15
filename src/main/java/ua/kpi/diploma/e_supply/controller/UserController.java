package ua.kpi.diploma.e_supply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.diploma.e_supply.dto.UserInfoDTO;
import ua.kpi.diploma.e_supply.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoDTO> getUserInfo(Authentication authentication) {
        // Кастимо до JwtAuthenticationToken і дістаємо Jwt
        Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
        // Зчитуємо email з поля claims
        String email = jwt.getClaimAsString("email");
        UserInfoDTO dto = userService.getUserInfoByEmail(email);
        return ResponseEntity.ok(dto);
    }
}