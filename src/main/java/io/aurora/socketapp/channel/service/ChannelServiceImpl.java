package io.aurora.socketapp.channel.service;

import io.aurora.socketapp.channel.domain.Channel;
import io.aurora.socketapp.channel.domain.User;
import io.aurora.socketapp.channel.repository.ChannelRepository;
import io.aurora.socketapp.channel.repository.UserRepository;
import io.aurora.socketapp.utils.Destinations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService
{
    private SimpMessagingTemplate simpMessagingTemplate;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;


    public ChannelServiceImpl(ChannelRepository channelRepository, UserRepository userRepository)
    {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public Channel save(Channel channel)
    {
        return channelRepository.save(channel);
    }

    @Override
    public void remove(Channel channel)
    {
        channelRepository.delete(channel);
    }

    @Override
    public Channel join(Channel channel, User user)
    {
        //Add channel for user
        List<String> channelIds = user.getChannels();
        if (channelIds.contains(channel.getId()) || (getUserIndex(user,channel.getUsers()) != -1))
        {
            updateConnectedUsersViaWebSocket(channel);
            return channel;
        }
        channelIds.add(channel.getId());
        user = user.toBuilder().channels(channelIds).build();
        userRepository.save(user);

        //Add user in Channel
        List<User> users = channel.getUsers();
        users.add(user);
        channel = channel.toBuilder().users(users).build();
        channelRepository.save(channel);
        updateConnectedUsersViaWebSocket(channel);
        return channel;
    }

    @Override
    public Channel leave(Channel channel, User user)
    {
        //Remove user in Channel
        List<User> users = channel.getUsers();
        int index = getUserIndex(user,users);
        users.remove(index);
        channel = channel.toBuilder().users(users).build();
        channelRepository.save(channel);

        //Remove channel for user
        List<String> channelIds = user.getChannels();
        channelIds.remove(channel.getId());
        user = user.toBuilder().channels(channelIds).build();
        userRepository.save(user);
        updateConnectedUsersViaWebSocket(channel);
        return channel;
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
    public List<Channel> getAllChannels()
    {
        List<Channel> channels = new ArrayList();
        channelRepository
                .findAll()
                .iterator()
                .forEachRemaining(channel->
                        channels.add(channel)
                );
        return channels;
    }

    @Override
    public List<String> getAllChannelIds()
    {
        List<String> channels = new ArrayList();
        channelRepository
                .findAll()
                .iterator()
                .forEachRemaining(channel->
                        channels.add(channel.getId())
                );
        return channels;
    }

    @Override
    public Channel findChannelById(String channelId)
    {
         return  channelRepository.findById(channelId).get();
    }

    @Override
    public void removeUserFromChannel(String channelId, String userId)
    {
        //Remove user in Channel
        Channel channel = channelRepository.findById(channelId).get();
        List<User> users = channel.getUsers();
        int index = getUserIndex(userRepository.findById(userId).get(),users);
        users.remove(index);
        channel = channel.toBuilder().users(users).build();
        channelRepository.save(channel);
    }

    @Override
    public List<User> findChannelOnlineUser(String channelId)
    {
        List<User> users = channelRepository.findById(channelId).get().getUsers();
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

    private void updateConnectedUsersViaWebSocket(Channel channel)
    {
        simpMessagingTemplate.convertAndSend(
                Destinations.Channel.connectedUsers(channel.getId()),
                findChannelOnlineUser(channel.getId()));
    }
}
