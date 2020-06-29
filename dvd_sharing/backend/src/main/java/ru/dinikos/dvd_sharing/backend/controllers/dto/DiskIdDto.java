/**
 * @author Ostrovskiy Dmitriy
 * @created 28.06.2020
 * DiskIdDto
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiskIdDto {

    @NotNull(message = "Not Null!")
    Long id;

    public void setId(Object id) {
        String idStr = id.toString();
        if (idStr instanceof String && idStr !=""){
            this.id = Long.parseLong(idStr);
        } else this.id = null;
    }
}
