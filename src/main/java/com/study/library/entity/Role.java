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
 // 데이터베이스 테이블과 매핑되는 객체인 엔터티 클래스를 담고 있습니다.
public class Role { //역할을 정해줌
    private int roleId;
    private String roleName;
    private String roleNameKor;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
