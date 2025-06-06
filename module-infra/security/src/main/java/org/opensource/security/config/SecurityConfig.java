package org.opensource.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.opensource.security.filter.JwtFilter;
import org.opensource.security.handler.JwtExceptionHandler;
import org.opensource.security.jwt.JwtUtil;
import org.opensource.user.port.out.persistence.UserPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserPersistencePort userPersistencePort;
    private final JwtUtil jwtUtil;

    // WebSocket 전용 SecurityFilterChain (우선순위 높음)
    @Bean
    @Order(1)
    public SecurityFilterChain webSocketSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/ws-booking-messaging", "/ws-booking-messaging/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())  // 모든 요청 허용
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/**",
                                        "/swagger-resources/**",
                                        "/webjars/**",
                                        "/api-docs/**",
                                        "/swagger-ui.html"
                                ).permitAll()
                                .requestMatchers(
                                        // email 관련 api 인증 없이 호출 허용
                                        "/api/v1/email/**",
                                        // user 관련 api 인증 없이 호출 허용
                                        "/api/v1/user/**",
                                        // 예외처리를 직접하지 않은 경우 예외 출력
                                        "/error").permitAll()
                                .requestMatchers(
                                        "/api/v1/book/**",
                                        "/api/v1/vote/votes/**",
                                        "/api/v1/chatroom/**",
                                        "/api/v1/user-chatroom/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // jwt filter 추가하여 토큰 검증
                .addFilterBefore(jwtFilter(userPersistencePort, jwtUtil), UsernamePasswordAuthenticationFilter.class)
                // jwt token error handler
                .addFilterBefore(jwtExceptionHandler(), JwtFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(
                List.of("http://localhost:5173",
                        "https://bookingopen.netlify.app/")
        );
        configuration.setAllowCredentials(true);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtFilter jwtFilter(UserPersistencePort userPersistencePort, JwtUtil jwtUtil) {
        return new JwtFilter(userPersistencePort, jwtUtil);
    }

    @Bean
    public JwtExceptionHandler jwtExceptionHandler() {
        return new JwtExceptionHandler(new ObjectMapper());
    }
}
