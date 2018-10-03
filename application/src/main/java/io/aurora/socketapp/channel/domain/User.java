package io.aurora.socketapp.channel.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document
@Getter
@NoArgsConstructor
public class User
{
    @Id
    private String id;
    private String name;
    private Boolean online;
    //TODO: replace with list
    private List<String> sessionIds;
    private List<String> channels;
    private List<String> subChannels;

    // ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ builder ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~  ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    @lombok.Builder(builderClassName = "Builder",builderMethodName = "newBuilder",toBuilder = true)
    public User(String id, String name, Boolean online, List<String> sessionIds, List<String> channels, List<String> subChannels)
    {
        this.id = id;
        this.name = name;
        this.online = online;
        this.sessionIds = sessionIds;
        this.channels = channels;
        this.subChannels = subChannels;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", online=" + online +
                ", sessionIds=" + sessionIds +
                ", channels=" + channels +
                ", subChannels=" + subChannels +
                '}';
    }

    public String toJsonString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ",\"name\":\"" + name + '\"' +
                ",\"online\":" + online +
                ", \"channels\":" + channels +
                ", \"subChannels\":" + subChannels +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, online, channels);
    }
}
