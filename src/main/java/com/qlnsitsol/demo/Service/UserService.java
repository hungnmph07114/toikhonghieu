package com.qlnsitsol.demo.Service;

import com.qlnsitsol.demo.entity.User;

import java.util.Optional;

public interface UserService {

     Optional<User> getByUserName(String s);
    Optional<User> getByEmail(String s);
    boolean existsByUserName(String s);
     boolean existsByEmail(String email);
      User save(User user);
}
