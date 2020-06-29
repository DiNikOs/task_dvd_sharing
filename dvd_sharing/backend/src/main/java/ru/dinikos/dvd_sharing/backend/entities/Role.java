/**
 * @author Ostrovskiy Dmitriy
 * @created 20.06.2020
 * Role
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
