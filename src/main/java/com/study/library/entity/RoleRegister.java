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

public class RoleRegister {// userid랑 roleid 연결 하는것
    private int roleRegisterId;
    private int userId;
    private int roleId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private Role role; //RoleRegister하나당 role가진다
}
