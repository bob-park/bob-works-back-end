package org.bobpark.client.configure;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.bobpark.client.common.utils.AuthenticationUtils;
import org.bobpark.client.common.utils.DocumentUtils;

@ConfigurationPropertiesScan("org.bobpark.client.configure.properties")
@Configuration
public class AppConfiguration {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3_600L);
        configuration.setExposedHeaders(
            List.of(
                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
                HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS,
                HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD,
                HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.CONTENT_DISPOSITION,
                HttpHeaders.CONTENT_LENGTH,
                HttpHeaders.ORIGIN,
                HttpHeaders.ACCEPT));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationUtils authenticationUtils(OAuth2AuthorizedClientService authorizedClientService) {
        return AuthenticationUtils.getInstance();
    }

    @Bean
    public DocumentUtils documentUtils(){
        return DocumentUtils.getInstance();
    }

}
