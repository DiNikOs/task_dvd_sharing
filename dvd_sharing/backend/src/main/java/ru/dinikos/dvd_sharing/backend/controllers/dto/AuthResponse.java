/**
 * @author Ostrovskiy Dmitriy
 * @created 26.06.2020
 * AuthResponse
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String token;
}
