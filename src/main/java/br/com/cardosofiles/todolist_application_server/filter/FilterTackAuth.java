package br.com.cardosofiles.todolist_application_server.filter;

import java.io.IOException;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.cardosofiles.todolist_application_server.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTackAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(FilterTackAuth.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    var servletPath = request.getServletPath();

    if (servletPath.startsWith("/tasks")) {

      var authorization = request.getHeader("Authorization");

      if (authorization == null || !authorization.startsWith("Basic ")) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
            "Authorization header missing or invalid");
        return;
      }

      var authEncoded = authorization.substring("Basic".length()).trim();
      byte[] authDecode = Base64.getDecoder().decode(authEncoded);
      var authString = new String(authDecode);

      String[] credentials = authString.split(":");
      if (credentials.length != 2) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid auth format");
        return;
      }

      String username = credentials[0];
      String password = credentials[1];

      logger.info("🔐 Autenticando usuário: " + username);

      var user = this.userRepository.findByUsername(username);

      if (user == null) {
        logger.warn("❌ Usuário não encontrado: " + username);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }

      var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

      if (passwordVerify.verified) {
        filterChain.doFilter(request, response);
      } else {
        logger.warn("❌ Senha incorreta para usuário: " + username);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      }

    } else {
      filterChain.doFilter(request, response);
    }
  }
}
