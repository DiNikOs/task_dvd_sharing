/**
 * @author Ostrovskiy Dmitriy
 * @created 19.06.2020
 * TakenItem
 * @version v1.0
 */


package ru.dinikos.dvd_sharing.backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
//@Data "java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "taken_item")
public class TakenItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "disk_id")
    private Long diskId;

}
