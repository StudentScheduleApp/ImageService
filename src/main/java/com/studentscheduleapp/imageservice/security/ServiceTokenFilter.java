package com.studentscheduleapp.imageservice.security;


import com.studentscheduleapp.imageservice.properties.GlobalProperties;
import com.studentscheduleapp.imageservice.services.AuthorizeServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceTokenFilter extends GenericFilterBean {


    @Autowired
    private GlobalProperties globalProperties;
    @Autowired
    private AuthorizeServiceService authorizeServiceService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
        final String token = getTokenFromRequest((HttpServletRequest) request);
        try {
            if (token != null && authorizeServiceService.authorize(token)) {
                final ServiceAuthentication appInfoToken = new ServiceAuthentication();
                appInfoToken.setAuthenticated(true);
                appInfoToken.setServiceName("service");
                SecurityContextHolder.getContext().setAuthentication(appInfoToken);
                Logger.getGlobal().info("authorize service success");
            }
            else
                Logger.getGlobal().info("authorize service failed: invalid token " + token);
        } catch (Exception e) {
            Logger.getGlobal().info("authorize service failed: " + e.getMessage());
        }
        fc.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String token = request.getHeader(globalProperties.getServiceTokenHeader());
        if (StringUtils.hasText(token)) {
            return token;
        }
        return null;
    }

}