package br.com.cardosofiles.todolist_application_server.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTackAuth extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(FilterTackAuth.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    var authorization = request.getHeader("Authorization");

    var authEncoded = authorization.substring("Basic".length()).trim();

    byte[] authDecode = Base64.getDecoder().decode(authEncoded);
    logger.info("AuthDecode: " + Arrays.toString(authDecode));
    var authString = new String(authDecode);

    String[] credentials = authString.split(":");
    String username = credentials[0];
    String password = credentials[1];

    logger.info("Username: " + username);
    logger.info("Password: " + password);

    // Continua o fluxo da aplicação
    filterChain.doFilter(request, response);
  }
}
