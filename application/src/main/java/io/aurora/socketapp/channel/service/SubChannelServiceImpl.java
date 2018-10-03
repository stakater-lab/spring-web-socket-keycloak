package io.aurora.socketapp.channel.service;

import io.aurora.socketapp.channel.domain.SubChannel;
import io.aurora.socketapp.channel.domain.User;
import io.aurora.socketapp.channel.repository.SubChannelRepository;
import io.aurora.socketapp.channel.repository.UserRepository;
import io.aurora.socketapp.utils.Destinations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubChannelServiceImpl implements SubChannelService
{
    private SimpMessagingTemplate simpMessagingTemplate;
    private final SubChannelRepository subChannelRepository;
    private final UserRepository userRepository;

    public SubChannelServiceImpl(SimpMessagingTemplate simpMessagingTemplate, SubChannelRepository subChannelRepository, UserRepository userRepository)
    {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.subChannelRepository = subChannelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SubChannel save(SubChannel subcChannel)
    {
        subChannelRepository.save(subcChannel);
        return subcChannel;
    }

    @Override
    public void remove(SubChannel subcChannel)
    {
        subChannelRepository.delete(subcChannel);
    }

    @Override
    public SubChannel join(String subChannelId, String userId)
    {
        User user = userRepository.findById(userId).get();
        SubChannel subChannel = subChannelRepository.findById(subChannelId).get();
        //Check if user exist in channel
        user = userRepository.findById(user.getId()).get();
        if(!user.getChannels().contains(subChannel.getChannelId()))
        {
            System.out.println(user.getChannels());
            System.out.println(subChannel.getChannelId());
            return null;
        }
        //Add channel for user
        List<String> subChannelIds = user.getSubChannels();
        if (subChannelIds.contains(subChannel.getId()) || (getUserIndex(user,subChannel.getUsers()) != -1))
        {
            updateConnectedUsersViaWebSocket(subChannel);
            return subChannel;
        }
        subChannelIds.add(subChannel.getId());
        user = user.toBuilder().subChannels(subChannelIds).build();
        userRepository.save(user);

        //Add user in Channel
        List<User> users = subChannel.getUsers();
        users.add(user);
        subChannel = subChannel.toBuilder().users(users).build();
        subChannelRepository.save(subChannel);
        updateConnectedUsersViaWebSocket(subChannel);
        return subChannel;
    }

    @Override
    public SubChannel leave(String subChannelId, String userId)
    {
        User user = userRepository.findById(userId).get();
        SubChannel subChannel = subChannelRepository.findById(subChannelId).get();
        //Check if user exist in channel
        user = userRepository.findById(user.getId()).get();
        if(!user.getChannels().contains(subChannel.getChannelId()))
        {
            return null;
        }
        //Remove user in Channel
        List<User> users = subChannel.getUsers();
        int index = getUserIndex(user,users);
        users.remove(index);
        subChannel = subChannel.toBuilder().users(users).build();
        subChannelRepository.save(subChannel);

        //Remove subChannel for user
        List<String> subChannelIds = user.getSubChannels();
        subChannelIds.remove(subChannel.getId());
        user = user.toBuilder().subChannels(subChannelIds).build();
        userRepository.save(user);
        updateConnectedUsersViaWebSocket(subChannel);
        return subChannel;
    }


    private int getUserIndex(User user, List<User> users)
    {
        for (User existingUser:users)
        {
            if (existingUser.equals(user))
            {
                return users.indexOf(existingUser);
            }
        }
        return -1;
    }

    @Override
    public List<SubChannel> getAllSubChannels()
    {
        List<SubChannel> subChannels = new ArrayList();
        subChannelRepository
                .findAll()
                .iterator()
                .forEachRemaining(subChannel->
                        subChannels.add(subChannel)
                );
        return subChannels;
    }

    @Override
    public List<String> getAllSubChannelIds()
    {
        List<String> subChannelIds = new ArrayList();
        subChannelRepository
                .findAll()
                .iterator()
                .forEachRemaining(channel->
                        subChannelIds.add(channel.getId())
                );
        return subChannelIds;
    }

    @Override
    public SubChannel findSubChannelById(String subChannelId)
    {
        return subChannelRepository.findById(subChannelId).get();
    }

    @Override
    public List<User> findChannelOnlineUser(String subChannelId)
    {
        List<User> users = subChannelRepository.findById(subChannelId).get().getUsers();
        List<User> onlineuser = new ArrayList();
        for (User user:users)
        {
            if(user.getOnline())
            {
                onlineuser.add(user);
            }
        }
        return onlineuser;
    }
    
    private void updateConnectedUsersViaWebSocket(SubChannel subChannel)
    {
        simpMessagingTemplate.convertAndSend(
                Destinations.SubChannel.connectedUsers(subChannel.getId()),
                findChannelOnlineUser(subChannel.getId()));
    }
}
