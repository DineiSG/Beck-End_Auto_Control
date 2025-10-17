package com.autoshopping.stock_control.api.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AutoLoginService autoLoginService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.consultas.url}")
    private String consultasUrl;

    @Value("${app.pendencias.url}")
    private String pendenciasUrl;

    private final RestTemplate restTemplate;

    @GetMapping("/refresh-session")
    public ResponseEntity<?> refreshSession() {
        autoLoginService.performLogin();

        if (autoLoginService.isSessionValid()) {
            return ResponseEntity.ok(autoLoginService.getCurrentUserData());
        }

        return ResponseEntity.status(401).body("Falha ao renovar sessão");
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser() {
        if (autoLoginService.isSessionValid()) {
            return ResponseEntity.ok(autoLoginService.getCurrentUserData());
        }

        return ResponseEntity.status(401).body("Sessão inválida");
    }

    @GetMapping("/current-token")
    public ResponseEntity<?> getCurrentToken() {
        String token = autoLoginService.getCurrentToken();

        if (token != null) {
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Token não disponível");
    }

    @GetMapping("/session-info")
    public ResponseEntity<?> getSessionInfo() {
        if (autoLoginService.isSessionValid()) {
            return ResponseEntity.ok(new SessionInfo(
                    autoLoginService.getCurrentIdLogin(),
                    autoLoginService.getCurrentToken(),
                    autoLoginService.getCurrentUserData()
            ));
        }

        return ResponseEntity.status(401).body("Sessão inválida");
    }

    @PostMapping("/consultas")
    public ResponseEntity<?> obterConsultas() {
        if (!autoLoginService.isSessionValid()) {
            return ResponseEntity.status(401).body("Sessão inválida");
        }

        String cookies = autoLoginService.getSessionCookies();
        if (cookies == null || cookies.isEmpty()) {
            return ResponseEntity.status(401).body("Cookies de sessão não disponíveis");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookies);
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Receba como String, não Object
        ResponseEntity<String> response = restTemplate.exchange(consultasUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Consultas obtidas");
            // Tente interpretar como JSON (mesmo que Content-Type seja text/html)
            try {
                return ResponseEntity.ok(objectMapper.readTree(response.getBody()));
            } catch (Exception e) {
                // Se não for JSON válido, retorne o corpo como string (para debug)
                return ResponseEntity.ok(response.getBody());
            }
        }

        return ResponseEntity.status(response.getStatusCode())
                .body("Erro ao obter consultas");
    }

    @PostMapping("/pendencias")
    public ResponseEntity<?> obterPendencias() {
        if (!autoLoginService.isSessionValid()) {
            return ResponseEntity.status(401).body("Sessão inválida");
        }

        String cookies = autoLoginService.getSessionCookies();
        if (cookies == null || cookies.isEmpty()) {
            return ResponseEntity.status(401).body("Cookies de sessão não disponíveis");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookies);
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Object> response = restTemplate.exchange(pendenciasUrl, HttpMethod.POST, entity, Object.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Pendencias obtidas");
            return ResponseEntity.ok(response.getBody());
        }

        return ResponseEntity.status(response.getStatusCode())
                .body("Erro ao obter pendências");
    }

    private record SessionInfo(String idLogin, String token, Object userData) {}
}