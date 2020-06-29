/**
 * @author Ostrovskiy Dmitriy
 * @created 25.06.2020
 * DiskDto
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiskResponseDto {

    private Long id;

    private LocalDateTime created;

    private String title;

    private String username;

    private String userTaken;

    public DiskResponseDto(Long id, LocalDateTime created, String title, String username, String userTaken) {
        this.id = id;
        this.created = created;
        this.title = title;
        this.username = username;
        this.userTaken = userTaken;
    }
}
