package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jt on 6/19/20.
 */
@Slf4j
public class RestHeaderAuthFilter implements Filter {

    public static final String API_KEY = "Api-Key";
    public static final String API_SECRET = "Api-Secret";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResp = (HttpServletResponse) servletResponse;

        String userName = httpServletReq.getHeader(API_KEY);
        String password = httpServletReq.getHeader(API_SECRET);

        if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)
                && userName.equals("spring") && password.equals("guru")){
             log.info("User {} successfully authenticated!!!!" ,userName);
             filterChain.doFilter(servletRequest,servletResponse);
        } else {
            httpServletResp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.info("Bad Credentials {}:{}, {}:{}",API_KEY,userName,API_SECRET,password);
        }

    }
}
