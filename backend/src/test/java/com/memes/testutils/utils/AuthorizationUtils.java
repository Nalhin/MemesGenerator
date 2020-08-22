package com.memes.testutils.utils;

import com.memes.auth.JwtService;
import org.springframework.http.HttpHeaders;

public class AuthorizationUtils {

    private final static JwtService jwtService = new JwtService(null);
    static {
        jwtService.init();
    }

    public static HttpHeaders authHeaders(String username){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer "+ jwtService.sign(username));
        return httpHeaders;
    }
}
