/**
 * @author Ostrovskiy Dmitriy
 * @created 26.06.2020
 * SecurityConstants
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;

@Log
public class SecurityConstants {

    @Value("${rest.api.url}")
    public final String REST_API_URL;

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }

}
