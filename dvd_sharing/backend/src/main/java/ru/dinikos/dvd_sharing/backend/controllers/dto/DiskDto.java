/**
 * @author Ostrovskiy Dmitriy
 * @created 28.06.2020
 * DiskDto
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.controllers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiskDto {

    private Long id;

    private LocalDateTime created;

    @NotEmpty(message = "Not Empty!")
    @Size(min = 1, message = "Too short <1!")
    private String title;

    private LocalDateTime lastDate;

    @NotNull(message = "Not Null!")
    private Long userId;


    public void setCreated(LocalDateTime created) {
        if (created==null || !(created instanceof LocalDateTime)) {
            created = LocalDateTime.now();
        }
        this.created = created;
    }

    public void setLastDate(LocalDateTime lastDate) {
        if (lastDate==null || !(lastDate instanceof LocalDateTime)) {
            lastDate = LocalDateTime.now();
        }
        this.lastDate = lastDate;
    }

    public void setId(Object id) {
       this.id = getId(id);
    }

    public void setUserId(Object userId) {
      this.userId = getId(userId);
    }

    private Long getId(Object id) {
        String idStr = id.toString();
        if (idStr instanceof String && idStr !=""){
            return Long.parseLong(idStr);
        } else return null;
    }
}
