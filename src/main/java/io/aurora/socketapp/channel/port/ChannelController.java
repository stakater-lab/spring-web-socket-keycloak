package io.aurora.socketapp.channel.port;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.aurora.socketapp.channel.domain.Channel;
import io.aurora.socketapp.channel.domain.User;
import io.aurora.socketapp.channel.service.ChannelService;
import io.aurora.socketapp.channel.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ChannelController
{
    private final ChannelService channelService;
    private final ObjectMapper mapper = new ObjectMapper();

    public ChannelController(ChannelService channelService, ObjectMapper mapper)
    {
        this.channelService = channelService;
    }

    @MessageMapping("/available.channels")
    @SendTo("/topic/channels")
    public List<String> getAllChannelIds()
    {
       return channelService.getAllChannelIds();
    }

    /*
    @MessageMapping("/channels/")
    @SendTo("/topic/{channelId}.connected.users")
    public List<User> getChannelById(@PathVariable String channelId)
    {
        return channelService.findChannelOnlineUser(channelId);
    }
    */

    @MessageMapping("/channels/join")
    public void join(String channelId, Principal principal) throws IOException
    {
        channelService.join(channelService.findChannelById(channelId)
                       ,mapper.readValue(principal.getName(),User.class));
    }

    @MessageMapping("/channels/leave")
    public void leave(String channelId, Principal principal) throws IOException
    {
        channelService.leave(channelService.findChannelById(channelId),
                mapper.readValue(principal.getName(),User.class));
    }

    @RequestMapping("/channels")
    public String getChannels(Map<String, Object> model){
        List<Channel> channels = channelService.getAllChannels();
        List<String> channelIds = new ArrayList();
        channels.iterator().forEachRemaining(channel -> {channelIds.add(channel.getId());});
        model.put("channels", channelIds);
        return "channels";
    }



}
