package bbw.tm.backend.configuration;

import bbw.tm.backend.account.AccountConverter;
import bbw.tm.backend.enums.Roles;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class WebConfiguration implements WebMvcConfigurer {

    private final AppConfiguration appConfiguration;
    private final JWTConfiguration jwtConfiguration;

    @Autowired
    public WebConfiguration(AppConfiguration appConfiguration, JWTConfiguration jwtConfiguration) {
        this.appConfiguration = appConfiguration;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:src/main/resources/uploads/");
    }

    @Override
    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        var corsRegistration = registry.addMapping("/uploads/**");

        if (appConfiguration.getAllowedOrigins() != null) {
            corsRegistration.allowedOrigins(appConfiguration.getAllowedOrigins());
        } else {
            corsRegistration.allowedOrigins("*");
        }

        corsRegistration.allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                    "Authorization Failed: " + authException.getMessage());
                        })
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new AccountConverter()))
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(auth -> auth
                        // Öffentliche Endpunkte
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/subjects/**").permitAll()
                        .requestMatchers("/grades/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/schools/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/teams/**").permitAll()

                        // Notfallkontakt-Endpunkte für authentifizierte Benutzer
                        .requestMatchers(HttpMethod.PATCH, "/persons/*/emergency-contact").authenticated()
                        .requestMatchers(HttpMethod.GET, "/persons/*/emergency-contact").authenticated()

                        // authentifizierte Endpunkte
                        .requestMatchers("/schools/**").authenticated()

                        .requestMatchers(appConfiguration.getAllowedUrls()).permitAll()
                        .requestMatchers(HttpMethod.POST, appConfiguration.getAuthUrls()).permitAll()

                        // admin-spezifische Endpunkte
                        .requestMatchers("/api/admin/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/accounts/*/role").hasRole(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/accounts/admin/create").hasRole(Roles.ADMIN.name())

                        // für Admin und Coach spezifische Endpunkte
                        .requestMatchers(HttpMethod.PUT, "/accounts/*/enabled").hasAnyRole(Roles.ADMIN.name(), Roles.COACH.name())
                        .requestMatchers(HttpMethod.DELETE, "/accounts/*").hasAnyRole(Roles.ADMIN.name(), Roles.COACH.name())
                        .requestMatchers(HttpMethod.PUT, "/persons/account/*/vacationdays").hasAnyRole(Roles.ADMIN.name(), Roles.COACH.name(), Roles.APPRENTICE.name())
                        .requestMatchers(HttpMethod.GET, "/persons/account/*/flextime").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/persons/account/*/flextime").hasAnyRole(Roles.ADMIN.name(), Roles.COACH.name(), Roles.APPRENTICE.name())
                        .requestMatchers(HttpMethod.GET, "/persons/**").authenticated()
                        .requestMatchers("/persons/**").hasAnyRole(Roles.ADMIN.name(), Roles.COACH.name())
                        .requestMatchers("/teams/**").hasAnyRole(Roles.ADMIN.name(), Roles.COACH.name())

                        // Alle anderen Anfragen erlauben
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        if (appConfiguration.getAllowedOrigins() != null) {
            config.setAllowedOrigins(List.of(appConfiguration.getAllowedOrigins()));
        } else {
            config.setAllowedOrigins(List.of("*"));
        }

        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec keySpec = new SecretKeySpec(
                jwtConfiguration.getSecret().getBytes(StandardCharsets.UTF_8),
                jwtConfiguration.getAlgorithm()
        );
        return NimbusJwtDecoder.withSecretKey(keySpec).build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/uploads/**");
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
