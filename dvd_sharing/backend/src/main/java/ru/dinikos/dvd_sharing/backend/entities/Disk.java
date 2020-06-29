/**
 * @author Ostrovskiy Dmitriy
 * @created 20.06.2020
 * Disk1
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
//@Data "java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "disks")
//@IdClass(Disk.DiskId.class)
public class Disk implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "title")
    private String title;

    @Column(name = "last_time_taken_date")
    private LocalDateTime lastDate;

//    @Id
    @Column(name = "user_id", nullable = false, insertable=false, updatable=false)
    private Long userId;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonManagedReference
    @OneToOne
    @JoinTable(name = "taken_item",
            joinColumns = @JoinColumn(name = "disk_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User userTaken;

    public Disk(LocalDateTime created, String title, LocalDateTime lastDate, User user) {
        this.created = created;
        this.title = title;
        this.lastDate = lastDate;
        this.user = user;
    }

    public String getTitle() {
        if (title == null) {
            title = "Title_default";
        }
        return title;
    }


    @Override
    public String toString() {
        return "Disks{" +
                "id=" + id +
                ", created=" + created +
                ", title='" + title + '\'' +
                ", lastDate=" + lastDate +
                ", userTaken=" + userTaken +
                ", userId='" + userId + '\'' +
                '}';
    }
}
