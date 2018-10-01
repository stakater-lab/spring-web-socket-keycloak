package io.aurora.socketapp.utils;

public class Destinations
{

	public static class Channel
	{
		
		public static String publicMessages(String channelId) {
			return "/topic/" + channelId;
		}
		
		public static String privateMessages(String channelId) {
			return "/queue/" + channelId;
		}
		
		public static String connectedUsers(String channelId) {
			return "/topic/" + channelId + "/connected.users";
		}
	}

	public static class SubChannel
	{

		public static String publicMessages(String channelId) {
			return "/topic/subchannel/" + channelId;
		}

		public static String privateMessages(String channelId) {
			return "/queue/subchannel/" + channelId;
		}

		public static String connectedUsers(String channelId)
		{
			return "/topic/subchannel/" + channelId + "/connected.users";
		}
	}
}
