package io.aurora.socketapp.channel.bootstrap;

import io.aurora.socketapp.channel.domain.Channel;
import io.aurora.socketapp.channel.domain.SubChannel;
import io.aurora.socketapp.channel.repository.ChannelRepository;
import io.aurora.socketapp.channel.repository.SubChannelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChannelBootstrap implements CommandLineRunner
{
    private final ChannelRepository channelRepository;
    private SubChannelRepository subChannelRepository;

    public ChannelBootstrap(ChannelRepository channelRepository, SubChannelRepository subChannelRepository)
    {
        this.channelRepository = channelRepository;
        this.subChannelRepository = subChannelRepository;
    }


    @Override
    public void run(String... args) throws Exception
    {
        loadChannels();
    }

    public void loadChannels()
    {
        Channel channel1 = new Channel("Ch-6c5b5a2a-add1","Test-Channel 1", new ArrayList(), new ArrayList());
        Channel channel2 = new Channel("Ch-6c5b5a2a-add2","Test-Channel 2", new ArrayList(), new ArrayList());
        Channel channel3 = new Channel("Ch-6c5b5a2a-add3","Test-Channel 3", new ArrayList(), new ArrayList());

        SubChannel subChannel1 = new SubChannel("Ch-sub-6c5b5a2a-add1","Test-SubChannel 1", new ArrayList(),"Ch-6c5b5a2a-add1");
        SubChannel subChannel2 = new SubChannel("Ch-sub-6c5b5a2a-add2","Test-SubChannel 2", new ArrayList(),"Ch-6c5b5a2a-add1");
        SubChannel subChannel3 = new SubChannel("Ch-sub-6c5b5a2a-add3","Test-SubChannel 3", new ArrayList(),"Ch-6c5b5a2a-add2");
        SubChannel subChannel4 = new SubChannel("Ch-sub-6c5b5a2a-add4","Test-SubChannel 4", new ArrayList(),"Ch-6c5b5a2a-add3");

        //channel1 = channel1.toBuilder().subChannels().build();
        List<SubChannel> subchannels1 = channel1.getSubChannels();
        List<SubChannel> subchannels2 = channel2.getSubChannels();
        List<SubChannel> subchannels3 = channel3.getSubChannels();

        //Add subchannels
        subchannels1.add(subChannel1);
        subchannels1.add(subChannel1);

        subchannels2.add(subChannel3);

        subchannels3.add(subChannel4);

        channel1 = channel1.toBuilder().subChannels(subchannels1).build();
        channel2 = channel2.toBuilder().subChannels(subchannels2).build();
        channel3 = channel3.toBuilder().subChannels(subchannels3).build();

        subChannelRepository.save(subChannel1);
        subChannelRepository.save(subChannel2);
        subChannelRepository.save(subChannel3);
        subChannelRepository.save(subChannel4);
        channelRepository.save(channel1);
        channelRepository.save(channel2);
        channelRepository.save(channel3);
    }
}
