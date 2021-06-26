package com.nikitagru.dto;

import lombok.Data;

/***
 * Данные аутетификации
 */
@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
