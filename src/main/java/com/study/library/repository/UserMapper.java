package com.study.library.repository;

import com.study.library.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {//0314-4
    public User findUserByUsername(String username);
    public int saveUser(User user);
    public int saveRole(int userId);
}
