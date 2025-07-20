package com.rustam.modern_dentistry.config;

import com.rustam.modern_dentistry.util.JwtAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;
import java.util.function.Supplier;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthUtil jwtAuthFilter;
    private final SuperAdminBypassAuthorizationManager superAdminBypassAuthorizationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x -> {
                    x.requestMatchers(getPublicEndpoints()).permitAll();
                    registerModulePermissions(x);
                    x.anyRequest().access(superAdminBypassAuthorizationManager);
                })
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(LogoutConfigurer::permitAll)
                .httpBasic(withDefaults())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .cors(cors -> cors.configurationSource(request -> getCorsConfiguration()))
                .build();
    }

    private void registerModulePermissions(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry x) {
        List<String> modules = List.of("patient", "appointment", "add-worker", "general-calendar", "patient-blacklist",
                "reservation", "technician", "workers-work-schedule");

        for (String module : modules) {
            String basePath = "/api/v1/" + module + "/**";

            x.requestMatchers(HttpMethod.GET, basePath).access((auth, ctx) -> decision(auth, basePath, "READ"));
            x.requestMatchers(HttpMethod.POST, basePath).access((auth, ctx) -> decision(auth, basePath, "CREATE"));
            x.requestMatchers(HttpMethod.PUT, basePath).access((auth, ctx) -> decision(auth, basePath, "UPDATE"));
            x.requestMatchers(HttpMethod.DELETE, basePath).access((auth, ctx) -> decision(auth, basePath, "DELETE"));
        }
    }

    private AuthorizationDecision decision(Supplier<Authentication> authSupplier, String path, String action) {
        Authentication auth = authSupplier.get();

        if (auth != null && auth.isAuthenticated()) {
            boolean isSuperAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("SUPER_ADMIN"));

            if (isSuperAdmin) {
                return new AuthorizationDecision(true);
            }

            String requiredPermission = path + ":" + action;
            boolean hasPermission = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals(requiredPermission));

            return new AuthorizationDecision(hasPermission);
        }

        return new AuthorizationDecision(false);
    }



    private String[] getPublicEndpoints() {
        return new String[]{
                "/api/v1/auth/login",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html/**"
        };
    }

    private String[] getUserRoleEndpoints() {
        return new String[]{
                "/**"
        };
    }

    private String[] getAdminRoleEndpoints() {
        return new String[]{
                "/api/v1/add-worker/create"
        };
    }

    private CorsConfiguration getCorsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        return corsConfiguration;
    }
}

