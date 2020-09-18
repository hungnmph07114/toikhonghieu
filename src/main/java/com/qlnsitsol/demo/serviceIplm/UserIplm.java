package com.qlnsitsol.demo.serviceIplm;

import com.qlnsitsol.demo.Service.UserService;
import com.qlnsitsol.demo.entity.User;
import com.qlnsitsol.demo.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserIplm implements UserService {
    @Autowired
    RepositoryUser repositoryUser;

    @Override
    public Optional<User> getByUserName(String s) {
        return repositoryUser.findByUserName(s);
    }

    @Override
    public Optional<User> getByEmail(String s) {
        return repositoryUser.findByEmail(s);
    }

    @Override
    public boolean existsByUserName(String s) {
        return repositoryUser.existsByUserName(s);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repositoryUser.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return repositoryUser.save(user);
    }
}
