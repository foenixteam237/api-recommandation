package com.ramseys.api_recommandation.dto;

import lombok.Data;

@Data
public class UserDTO {
    public UserDTO(Long id2, String username2, String email2) {
       this.id = id2;
       this.username = username2;
       this.email = email2;
    }
    private Long id;
    private String username;
    private String email;
}