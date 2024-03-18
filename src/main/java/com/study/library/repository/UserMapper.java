package com.study.library.repository;

import com.study.library.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
// 데이터베이스와의 상호작용을 담당하는 리포지토리 클래스를 포함합니다. 데이터베이스에 접근하여 데이터를 조작합니다.
public interface UserMapper {//0314-4
    public User findUserByUsername(String username);
    public int saveUser(User user);
    public int saveRole(int userId);
}
