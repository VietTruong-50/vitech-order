package vn.vnpt.config.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${keycloak.realm-master}")
    private String realmMaster;

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.credentials.username}")
    private String username;

    @Value("${keycloak.credentials.password}")
    private String password;

    @Value("${keycloak.credentials.client-id}")
    private String clientId;

    @Value("${keycloak.realm}")
    private String realm;

    @Bean
    public RealmResource realmResource() {
        Keycloak keycloak = Keycloak.getInstance(
                authServerUrl,
                realmMaster,
                username,
                password,
                clientId);
        return keycloak.realm(realm);
    }
}
