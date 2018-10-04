package io.aurora.socketapp.channel.service;

import io.aurora.socketapp.channel.domain.Channel;
import io.aurora.socketapp.channel.domain.User;

import java.util.List;
import java.util.Optional;

public interface ChannelService
{
    Channel save(Channel channel);

    void remove(Channel channel);

    Channel join(String channelId, String userId);

    Channel leave(String channelId, String userId);

    List<Channel> getAllChannels();

    List<String> getAllChannelIds();

    Channel findChannelById(String channelId);

    List<User> findChannelOnlineUser(String channelId);
}
