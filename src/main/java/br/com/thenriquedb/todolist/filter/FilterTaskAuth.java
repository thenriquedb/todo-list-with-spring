package br.com.thenriquedb.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.thenriquedb.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;

    private String[] decodeCredentials(String authorizationHeader) {
        var authEncoded = authorizationHeader.substring("Basic".length()).trim();

        byte[] authDecode = Base64.getDecoder().decode(authEncoded);

        var authString = new String(authDecode);

        return authString.split(":");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var servletPath = request.getServletPath().trim();

        if(!servletPath.equals("/tasks")) {
            filterChain.doFilter(request, response);
        } else {
            var authorization = request.getHeader("Authorization");

            var credentials = this.decodeCredentials(authorization);
            var username = credentials[0];
            var password = credentials[1];

            var user = this.userRepository.findByUsername(username);

            if(user == null) {
                response.sendError(401);
            } else {
                request.setAttribute("userId", user.getId());

                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if (passwordVerify.verified) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        }
    }
}
