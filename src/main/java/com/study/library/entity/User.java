package com.study.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class User {//0314 -3
    private int userId;
    private String username;
    private String password;
    private String name;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
