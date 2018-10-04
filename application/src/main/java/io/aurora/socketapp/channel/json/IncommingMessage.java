package io.aurora.socketapp.channel.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class IncommingMessage
{
    @JsonProperty("userId")
    String userId;
    @JsonProperty("channelId")
    String channelId;


    // ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ builder ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~  ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    @lombok.Builder(builderClassName = "Builder",builderMethodName = "newBuilder",toBuilder = true)
    public IncommingMessage(String userId, String channelId)
    {
        this.userId = userId;
        this.channelId = channelId;
    }
}
