package ua.kpi.diploma.e_supply.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ua.kpi.diploma.e_supply.dto.AuthDTO;
import ua.kpi.diploma.e_supply.dto.LogoutRequestDTO;
import ua.kpi.diploma.e_supply.dto.TokenRefreshRequestDTO;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;

    @Value("${client-id}")
    private String clientId;

    @Value("${resource-url}")
    private String authUrl;

    @Value("${logout-url}")
    private String logoutUrl;

    public ResponseEntity<String> authenticate(AuthDTO authDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("username", authDTO.login());
        form.add("password", authDTO.password());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        return restTemplate.postForEntity(authUrl, request, String.class);
    }

    public ResponseEntity<String> refreshAccessToken(TokenRefreshRequestDTO tokenRefreshRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "refresh_token");
        form.add("client_id", clientId);
        form.add("refresh_token", tokenRefreshRequestDTO.refreshToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        return restTemplate.postForEntity(authUrl, request, String.class);
    }

    public void logout(LogoutRequestDTO logoutRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("refresh_token", logoutRequestDTO.refreshToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        restTemplate.exchange(logoutUrl, HttpMethod.POST, request, Void.class);
    }
}
