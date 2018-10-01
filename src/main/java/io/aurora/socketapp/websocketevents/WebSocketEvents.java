package io.aurora.socketapp.websocketevents;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.aurora.socketapp.channel.domain.User;
import io.aurora.socketapp.channel.repository.UserRepository;
import io.aurora.socketapp.channel.service.ChannelService;
import io.aurora.socketapp.channel.service.SubChannelService;
import io.aurora.socketapp.channel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebSocketEvents
{
	@Autowired
	private UserService userService;

	private UserRepository userRepository;

	SimpMessageSendingOperations messagingTemplate;

	private ChannelService channelService;

	private SubChannelService subChannelService;


	public WebSocketEvents(UserService userService, UserRepository userRepository,
						   SimpMessageSendingOperations messagingTemplate, ChannelService channelService, SubChannelService subChannelService)
	{
		this.userService = userService;
		this.userRepository = userRepository;
		this.messagingTemplate = messagingTemplate;
		this.channelService = channelService;
		this.subChannelService = subChannelService;
	}

	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) throws IOException
	{
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = headers.getUser().getName();
		User user = mapper.readValue(jsonInString,User.class);
		if (user == null)
		{
			return;
		}
		String id = user.getId();
		MessageHeaders headers2 = event.getMessage().getHeaders();
		String sessionId = SimpMessageHeaderAccessor.getSessionId(headers2);

		if(userService.checkUserExist(id))
		{
			List<String> sessionIds = new ArrayList();
			sessionIds.add(sessionId);
			userRepository.save(
					new User(id, user.getName(), true, sessionIds, new ArrayList(), new ArrayList()));

		}
		else
		{
			User userObject = userRepository.findById(id).get();
			List<String> sessionIds = userObject.getSessionIds();
			sessionIds.add(sessionId);
			userObject = userObject.toBuilder().sessionIds(sessionIds).online(true).build();
			userRepository.save(userObject);
		}
		//TODO: Send request to all joined channels and sub channels if needed

	}

	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event)
	{
		String sessionId = event.getSessionId();
		if (sessionId == null)
		{
			return;
		}
		userRepository.findBySessionIdsEquals(sessionId).ifPresent((user) ->
		{
			//Set User Offline and remove from every Channel
			user = user.toBuilder().online(false).build();

			//Remove channel and subchannels from user and vice versa
			for (String channelId:user.getChannels())
			{
				channelService.removeUserFromChannel(channelId,user.getId());
			}
			for (String subChannelId:user.getSubChannels())
			{
				subChannelService.removeUserFromSubChannel(subChannelId,user.getId());
			}

			user = user.toBuilder().subChannels(new ArrayList()).channels(new ArrayList()).build();

            List<String> sessionIds = user.getSessionIds();
			sessionIds.remove(sessionId);
 			user = user.toBuilder().channels(new ArrayList()).sessionIds(sessionIds).build();
			userRepository.save(user);
		});
		//TODO: Send request to all joined channels and sub channels if needed
	}
}
