/**
 * @author Ostrovskiy Dmitriy
 * @created 26.06.2020
 * AuthRequest
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequest {

    @NotNull(message = "Not Null!")
    @Size(min = 4, max = 30, message = "Too short <4!")
    private String username;
    @NotNull(message = "Not Null!")
    @Size(min = 4, max = 30, message = "Too short <4!")
    private String password;
}
