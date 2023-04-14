package com.example.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {
    String sendValidateEmail(String email, String sessionId, Boolean hasAccount);
    String validateAndRegister(String username, String password, String email, String code, String sessionId);

    String validateForReset(String email, String code, String sessionId);

    Boolean resetPassword(String email, String password);
}
