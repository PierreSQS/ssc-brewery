package guru.sfg.brewery.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

/**
 * Modified by Pierrot on 1/18/2023
 */
@Slf4j
public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String userName = getUsername(request);
        String password = getPassword(request);

        if (userName == null){
            userName = "";
        }

        if (password == null){
            password = "";
        }

        log.debug("Authenticating User: " + userName);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);

        // if header credentials present, then authenticate
        if (StringUtils.hasText(userName) && StringUtils.hasText(password)) { // we only check the Api-Keys exists, for security reasons
            return this.getAuthenticationManager().authenticate(token);
        } else {
            // if not skip the filtering. We want then to continue the filter chain
            // See implementation of the doFilter()-Method
            return null;
        }

    }

    private String getPassword(HttpServletRequest request) {
        return request.getHeader("Api-Secret");
    }

    private String getUsername(HttpServletRequest request) {
        return request.getHeader("Api-Key");
    }
}
