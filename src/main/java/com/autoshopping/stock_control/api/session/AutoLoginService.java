package com.autoshopping.stock_control.api.session;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import static org.springframework.util.StringUtils.truncate;

@Getter
@Service
@Slf4j
public class AutoLoginService {

    @Value("${app.login.usuario}")
    private String usuario;

    @Value("${app.login.senha}")
    private String senha;

    @Value("${app.token.url}")
    private String tokenUrl;

    @Value("${app.userdata.url}")
    private String userDataUrl;

    @Value("${app.external.hash}")
    private String externalHash;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Não há token de autenticação Bearer — autenticação é por sessão (cookies)
    // @Getter private String currentToken; // removido

    @Getter
    private JsonNode currentUserData;

    @Getter
    private String sessionCookies;

    public AutoLoginService() {
        ClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                super.prepareConnection(connection, httpMethod);
                connection.setInstanceFollowRedirects(false);
            }
        };
        this.restTemplate = new RestTemplate(factory);
    }

    @PostConstruct
    public void initializeSession() {
        performLogin();
    }

    public void performLogin() {
        try {
            String cookies = executeLogin();
            if (cookies != null) {
                this.sessionCookies = cookies;

                if (completeSessionInitialization(cookies)) {
                    // Obtém o ID numérico do login (chamado de "token" na API)
                    Long loginId = fetchLoginId(cookies);
                    if (loginId != null) {
                        this.currentUserData = fetchUserData(loginId);
                        if (this.currentUserData != null) {
                            log.info("Login automático realizado com sucesso");
                        }
                    } else {
                        log.error("Falha ao obter ID de login");
                    }
                } else {
                    log.error("Falha ao inicializar sessão em administrativo.php");
                }
            } else {
                log.error("Falha no login inicial");
            }
        } catch (Exception e) {
            log.error("Erro ao realizar login automático: {}", e.getMessage(), e);
        }
    }

    private String executeLogin() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

            MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.add("Codigo", usuario);
            form.add("Senha", senha);
            form.add("geral", "Login");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

            // URL corrigida: sem espaços!
            String loginUrl = "https://ambteste.credtudo.com.br/painel/login.php";
            ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, request, String.class);

            List<String> setCookieHeaders = response.getHeaders().get(HttpHeaders.SET_COOKIE);
            if (setCookieHeaders == null || setCookieHeaders.isEmpty()) {
                log.warn("Nenhum cookie de sessão foi retornado após login");
                return null;
            }

            String cookies = String.join("; ", setCookieHeaders);
            log.info("Cookies de sessão capturados com sucesso");
            return cookies;

        } catch (Exception e) {
            log.error("Erro ao executar login: {}", e.getMessage(), e);
            return null;
        }
    }

    private boolean completeSessionInitialization(String cookies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.COOKIE, cookies);
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            String adminUrl = "https://ambteste.credtudo.com.br/painel/administrativo.php";
            ResponseEntity<String> response = restTemplate.exchange(
                    adminUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Sessão completada com sucesso ao acessar administrativo.php");
                return true;
            } else {
                log.warn("Acesso a administrativo.php falhou com status: {}", response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            log.error("Erro ao completar inicialização da sessão: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Obtém o ID numérico do login.
     * Observação: nesse sistema, o campo "token" na resposta da API é na verdade o id_login (número).
     */
    private Long fetchLoginId(String cookies) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.COOKIE, cookies);
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                log.info("Resposta do endpoint de token: {}", response.getBody());

                JsonNode json = objectMapper.readTree(response.getBody());
                if (json.has("dados") && json.get("dados").has("token")) {
                    String tokenStr = json.get("dados").get("token").asText().trim();
                    try {
                        return Long.parseLong(tokenStr);
                    } catch (NumberFormatException e) {
                        log.error("Valor de 'token' não é um número válido: {}", tokenStr);
                        return null;
                    }
                } else {
                    log.warn("Campo 'dados.token' não encontrado na resposta");
                    return null;
                }
            }
            return null;
        } catch (Exception e) {
            log.error("Erro ao obter ID de login: {}", e.getMessage(), e);
            return null;
        }
    }

    private JsonNode fetchUserData(Long loginId) {
        try {
            String url = userDataUrl + loginId;
            log.info("Buscando dados do usuário em: {}", url);
            log.info("Enviando Hash no header: {}", externalHash);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Hash", externalHash);
            headers.set(HttpHeaders.COOKIE, sessionCookies);
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // 👇 Log truncado para evitar poluição
            log.info("Resposta recebida (resumo): {}", truncate(response.getBody(), 300));

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return objectMapper.readTree(response.getBody());
            }

            log.warn("Falha ao obter dados do usuário. Status: {}", response.getStatusCode());
            return null;

        } catch (Exception e) {
            log.error("Erro ao obter dados do usuário: {}", e.getMessage(), e);
            return null;
        }
    }

    public boolean isSessionValid() {
        return currentUserData != null;
    }

    public String getCurrentToken() {
        return "";
    }

    public String getCurrentIdLogin() {
        return "";
    }
}