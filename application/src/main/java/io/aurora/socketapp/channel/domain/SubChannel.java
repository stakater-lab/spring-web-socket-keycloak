package io.aurora.socketapp.channel.domain;


import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
public class SubChannel
{
    @Id
    public String id;
    public String name;
    @DBRef
    private List<User> users;
    private String channelId;

    // ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ builder ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~  ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    @lombok.Builder(builderClassName = "Builder",builderMethodName = "newBuilder",toBuilder = true)
    public SubChannel(String id, String name, List<User> users, String channelId)
    {
        this.id = id;
        this.name = name;
        this.users = users;
        this.channelId = channelId;
    }
}
