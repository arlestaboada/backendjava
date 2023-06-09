package com.arles.backendjava.security;

import com.arles.backendjava.applicationContext.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_DATE = 864000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String TOKEN_SECRET = "YbHGU2lYh5ZPrOxkDp386BTZENaEdv9E";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();

    }

}
