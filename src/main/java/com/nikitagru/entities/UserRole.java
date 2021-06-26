package com.nikitagru.entities;

import com.nikitagru.entities.id.UserRoleId;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/***
 * Роли пользователей
 */
@Entity
@Data
@Table(name = "users_roles")
@IdClass(UserRoleId.class)
public class UserRole {

    @Id
    private long user_id;
    @Id
    private long role_id;
}
