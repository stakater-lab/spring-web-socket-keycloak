package io.aurora.socketapp.channel.port;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.aurora.socketapp.channel.domain.User;
import io.aurora.socketapp.channel.service.SubChannelService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class SubChannelController
{
    private SubChannelService subChannelService;
    private final ObjectMapper mapper = new ObjectMapper();

    public SubChannelController(SubChannelService subChannelService)
    {
        this.subChannelService = subChannelService;
    }

    @MessageMapping("/available.subshannels")
    @SendTo("/topic/subchannels")
    public List<String> getAllChannelIds()
    {
        return subChannelService.getAllSubChannelIds();
    }

    @MessageMapping("/subchannels/join")
    public void join(String subChannelId, Principal principal) throws IOException
    {
        subChannelService.join(subChannelId
                ,mapper.readValue(principal.getName(), User.class).getId());
    }

    @MessageMapping("/subchannels/leave")
    public void leave(String subChannelId, Principal principal) throws IOException
    {
        subChannelService.leave(subChannelId,
                mapper.readValue(principal.getName(),User.class).getId());
    }
}
