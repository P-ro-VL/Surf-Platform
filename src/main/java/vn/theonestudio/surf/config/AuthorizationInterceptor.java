package vn.theonestudio.surf.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import vn.theonestudio.surf.api.ApiCallException;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorizationInterceptor implements HandlerInterceptor {

    public static final List<String> PUBLIC_ENDPOINTS = List.of(
            "v1/team",
            "v1/document/view",
            "v1/gate/auth"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        for(String publicEndpoint : PUBLIC_ENDPOINTS) {
            if(path.contains(publicEndpoint)) return true;
        }

        if(path.split("/").length == 2) return true;

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equalsIgnoreCase("anonymousUser")) {
            throw new ApiCallException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        return true;
    }
}