package com.nikitagru.entities.id;

import java.io.Serializable;

/***
 * Id для таблицы о принадлежности пользователя к роли
 */
public class UserRoleId implements Serializable {
    private long user_id;
    private long role_id;

    public UserRoleId() {
    }

    public UserRoleId(long user_id, long role_id) {
        this.user_id = user_id;
        this.role_id = role_id;
    }
}
