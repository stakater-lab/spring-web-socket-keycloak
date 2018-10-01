package io.aurora.socketapp.channel.service;

import io.aurora.socketapp.channel.domain.Channel;
import io.aurora.socketapp.channel.domain.User;

import java.util.List;
import java.util.Optional;

public interface ChannelService
{
    Channel save(Channel channel);

    void remove(Channel channel);

    Channel join(Channel channel, User user);

    Channel leave(Channel channel, User user);

    List<Channel> getAllChannels();

    List<String> getAllChannelIds();

    Channel findChannelById(String channelId);

    void removeUserFromChannel(String channelId,String userId);

    List<User> findChannelOnlineUser(String channelId);
}
