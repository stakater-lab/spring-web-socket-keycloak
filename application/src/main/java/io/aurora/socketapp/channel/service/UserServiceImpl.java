package io.aurora.socketapp.channel.service;

import io.aurora.socketapp.channel.domain.User;
import io.aurora.socketapp.channel.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean checkUserExist(String id)
    {
        if(userRepository.findById(id) != null){
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public User getUserById(String id)
    {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> findAllOnlineUsers(boolean online)
    {
        return userRepository.findByOnline(online);
    }
}
