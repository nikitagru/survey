package com.nikitagru.entities;

import lombok.Data;

import javax.persistence.*;

/***
 * Роль пользователя
 */
@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
