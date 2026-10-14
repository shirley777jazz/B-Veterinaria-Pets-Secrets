package com.PetsSecrets.Veterinaria_Backend.config;

import com.PetsSecrets.Veterinaria_Backend.jwt.JwtAuthenticationFilter;
import com.PetsSecrets.Veterinaria_Backend.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Value("${app.jwt.secret:defaultSecret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:86400000}")
    private Long jwtExpirationMs;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // CORS preflight
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                
                // Rutas públicas de autenticación y recursos
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/uploads/**").permitAll()
                
                // Endpoints específicos de usuario autenticado (deben ir antes de patrones genéricos)
                .requestMatchers("/api/mascotas/mis-mascotas").authenticated()
                .requestMatchers("/api/citas/mis-citas", "/api/citas/proximas").authenticated()
                .requestMatchers("/api/adopciones/mis-solicitudes").authenticated()
                .requestMatchers("/api/pedidos/mis-pedidos", "/api/pedidos/usuario", "/api/pedidos/crear-desde-carrito").authenticated()
                .requestMatchers("/api/carrito", "/api/carrito/**").authenticated()
                
                // Endpoints GET públicos para visualización de catálogo
                .requestMatchers(HttpMethod.GET, "/api/productos", "/api/productos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categorias", "/api/categorias/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/servicios", "/api/servicios/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/sedes", "/api/sedes/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/mascotas", "/api/mascotas/disponibles", "/api/mascotas/buscar", "/api/mascotas/filtrar", "/api/mascotas/{id}").permitAll()
                
                // Endpoints de administración y gestión (protegidos además con @PreAuthorize)
                .requestMatchers("/api/estadisticas", "/api/estadisticas/**").authenticated()
                .requestMatchers("/api/usuarios", "/api/usuarios/**").authenticated()
                .requestMatchers("/api/pedidos", "/api/pedidos/**").authenticated()
                .requestMatchers("/api/citas", "/api/citas/**").authenticated()
                .requestMatchers("/api/adopciones", "/api/adopciones/**").authenticated()
                
                // Operaciones de modificación (POST, PUT, DELETE) requieren autenticación
                .requestMatchers(HttpMethod.POST, "/api/productos/**", "/api/categorias/**", "/api/mascotas/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/productos/**", "/api/categorias/**", "/api/mascotas/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**", "/api/categorias/**", "/api/mascotas/**").authenticated()
                
                .anyRequest().permitAll()
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public Long getJwtExpirationMs() {
        return jwtExpirationMs;
    }
}
