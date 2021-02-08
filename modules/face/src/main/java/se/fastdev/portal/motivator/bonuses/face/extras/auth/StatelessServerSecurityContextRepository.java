package se.fastdev.portal.motivator.bonuses.face.extras.auth;

import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class StatelessServerSecurityContextRepository
    implements ServerSecurityContextRepository {

  private final Function<ServerHttpRequest, Authentication> authFromRequest;
  private final Consumer<Throwable> errorReport;

  public StatelessServerSecurityContextRepository(
      Function<ServerHttpRequest, Authentication> authFromRequest,
      Consumer<Throwable> errorReport
  ) {
    this.authFromRequest = authFromRequest;
    this.errorReport = errorReport;
  }

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    return Mono.error(new RuntimeException("unexpected call of save(SecurityContext)"));
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    return Mono.just(exchange.getRequest())
               .map(authFromRequest)
               .<SecurityContext>map(SecurityContextImpl::new)
               .onErrorResume(err -> {
                 errorReport.accept(err);
                 return Mono.empty();
               })
               .cache();
  }
}
