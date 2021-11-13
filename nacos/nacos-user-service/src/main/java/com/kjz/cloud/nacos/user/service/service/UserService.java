package com.kjz.cloud.nacos.user.service.service;

import com.kjz.cloud.nacos.user.service.domain.User;

import java.util.List;

public interface UserService {
    void create(User user);

    User getUser(Long id);

    void update(User user);

    void delete(Long id);

    User getByUsername(String username);

    List<User> getUserByIds(List<Long> ids);
}
