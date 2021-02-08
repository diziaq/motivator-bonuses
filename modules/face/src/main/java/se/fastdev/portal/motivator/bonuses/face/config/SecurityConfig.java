package se.fastdev.portal.motivator.bonuses.face.config;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import se.fastdev.portal.motivator.bonuses.face.extras.auth.StatelessServerSecurityContextRepository;
import se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.JwtAuthenticationProvider;

@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, Logger logger) {
    final var repo = createContextRepository(logger);

    return http
               .authorizeExchange(
                   e -> e
                            .pathMatchers("/admin/**").hasAuthority("motivator.admin.read")
                            .pathMatchers("/supervisor/**").hasAuthority("motivator.admin.read")
                            .pathMatchers("/anyuser/**").hasAuthority("motivator.anyuser.read")
                            .anyExchange().authenticated()
               )
               .securityContextRepository(repo)
               .httpBasic().disable()
               .csrf().disable()
               .cors().disable()
               .formLogin().disable()
               .logout().disable()
               .build();
  }

  private static StatelessServerSecurityContextRepository createContextRepository(Logger logger) {
    final var authProvider = JwtAuthenticationProvider.createDefault();

    return new StatelessServerSecurityContextRepository(
        authProvider::fromRequest,
        e -> logger.error("Authentication failed", e)
    );
  }
}
