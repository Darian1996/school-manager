package com.darian.schoolmanager.login.configuration;

import org.springframework.security.core.AuthenticationException;

public class AnonymousAuthenticationException extends AuthenticationException {

    public AnonymousAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public AnonymousAuthenticationException(String msg) {
        super(msg);
    }
}