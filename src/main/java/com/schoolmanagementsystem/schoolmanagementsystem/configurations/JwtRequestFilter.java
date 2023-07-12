package com.schoolmanagementsystem.schoolmanagementsystem.configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolmanagementsystem.schoolmanagementsystem.models.response.ResponseMessage;
import com.schoolmanagementsystem.schoolmanagementsystem.models.response.ResponseStatus;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if ("/protected" == uri) {
            String requestTokenHeader = request.getHeader("Authorization");
            String jwtToken;
            AuthPrincipal authPrincipal;
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    authPrincipal = jwtService.getAllClaimsFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    logger.info("Unable to fetch JWT Token", e);
                    ResponseMessage responseMessage = new ResponseMessage(401, ResponseStatus.Failure,"Unable to validate signature from JWT Token, please check the format");
                    PrintWriter writer = response.getWriter();
                    response.setContentType("application/json");
                    response.setStatus(401);
                    writer.write(convertObjectToJson(responseMessage));
                    writer.flush();
                    return;
                } catch (ExpiredJwtException e) {
                    logger.info("JWT Token has expired", e);
                    ResponseMessage responseMessage = new ResponseMessage(401,ResponseStatus.Failure,"JWT Token has expired");
                    PrintWriter writer = response.getWriter();
                    response.setStatus(401);
                    response.setContentType("application/json");
                    writer.write(convertObjectToJson(responseMessage));
                    writer.flush();
                    return;
                } catch (Exception e) {
                    logger.info("Jwt validation failed", e);
                    ResponseMessage responseMessage = new ResponseMessage(401,ResponseStatus.Failure,"JWT validation failed");
                    PrintWriter writer = response.getWriter();
                    response.setStatus(401);
                    response.setContentType("application/json");
                    writer.write(convertObjectToJson(responseMessage));
                    writer.flush();
                    return;
                }
            } else {
                logger.error("Token passed is null/JWT Token does not begin with Bearer String");
                logger.info("Incorrect Jwt Token format");
                ResponseMessage responseMessage = new ResponseMessage(401,ResponseStatus.Failure,"Incorrect Jwt Token format");
                PrintWriter writer = response.getWriter();
                response.setStatus(401);
                response.setContentType("application/json");
                writer.write(convertObjectToJson(responseMessage));
                writer.flush();
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SSOAuthentication ssoAuthentication = new SSOAuthentication();
                ssoAuthentication.setPrincipal(authPrincipal);
                ssoAuthentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(ssoAuthentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String convertObjectToJson(Object obj) throws JsonProcessingException {
        if (obj == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
