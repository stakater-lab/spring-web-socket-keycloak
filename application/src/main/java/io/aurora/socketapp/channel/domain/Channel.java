package io.aurora.socketapp.channel.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document
@Getter
public class Channel
{
    @Id
    private String id;
    private String name;
    @DBRef
    private List<User> users;
    @DBRef
    private List<SubChannel> subChannels;

    // ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ builder ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~  ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    @lombok.Builder(builderClassName = "Builder",builderMethodName = "newBuilder",toBuilder = true)
    public Channel(String id, String name, List<User> users, List<SubChannel> subChannels)
    {
        this.id = id;
        this.name = name;
        this.users = users;
        this.subChannels = subChannels;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
