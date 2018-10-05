package io.aurora.socketapp.channel.mapper;

import io.aurora.socketapp.channel.domain.User;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserMapper
{
    public User accessTokenToUserMapper(AccessToken accessToken)
    {
        return User.newBuilder()
                .id(accessToken.getPreferredUsername())
                .name(accessToken.getName())
                .sessionIds(new ArrayList())
                .online(true)
                .channels(new ArrayList())
                .subChannels(new ArrayList())
                .build();
    }
}
