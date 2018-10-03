package io.aurora.socketapp.channel.service;

import io.aurora.socketapp.channel.domain.User;

import java.util.List;

public interface UserService
{
    Boolean checkUserExist(String id);

    User getUserById(String id);

    List<User> findAllOnlineUsers(boolean online);
}
