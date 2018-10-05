package io.aurora.socketapp.channel.service;

import io.aurora.socketapp.channel.domain.SubChannel;
import io.aurora.socketapp.channel.domain.User;

import java.util.List;

public interface SubChannelService
{
    SubChannel save(SubChannel channel);

    void remove(SubChannel channel);

    SubChannel join(String channelId, String userId);

    SubChannel leave(String channelId, String userId);

    List<SubChannel> getAllSubChannels();

    List<String> getAllSubChannelIds();

    SubChannel findSubChannelById(String channelId);

    List<User> findChannelOnlineUser(String channelId);
}
