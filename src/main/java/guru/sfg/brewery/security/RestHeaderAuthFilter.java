package guru.sfg.brewery.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Modified by Pierrot on 1/18/2023
 */
@Slf4j
public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    /**
     * The Requirements of the overwritten Implementation:
     <ol>
     * <li> we authenticate with the headers Api-Keys if available</li>
     * <li> if not we continue the filter chain.
     * </ol>
     * @param request: the request sent
     * @param response: the response received
     * @param chain: the filter chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        if (!requiresAuthentication(httpReq, httpResp)) {
            chain.doFilter(httpReq, response);
            return;
        }
        try {
            Authentication authenticationResult = attemptAuthentication(httpReq, httpResp);
            if (authenticationResult != null) {
                successfulAuthentication(httpReq, httpResp, chain, authenticationResult);
            } else {
                chain.doFilter(request,response);
            }

        }
        catch (InternalAuthenticationServiceException failed) {
            this.logger.error("An internal error occurred while trying to authenticate the user.", failed);
            unsuccessfulAuthentication(httpReq, httpResp, failed);
        }
        catch (AuthenticationException ex) {
            // Authentication failed
            unsuccessfulAuthentication(httpReq, httpResp, ex);
        }
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

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {

        SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authResult);
        securityContextHolderStrategy.setContext(context);

        SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
        securityContextRepository.saveContext(context, request, response);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
        }
    }


    private String getPassword(HttpServletRequest request) {
        return request.getHeader("Api-Secret");
    }

    private String getUsername(HttpServletRequest request) {
        return request.getHeader("Api-Key");
    }
}
