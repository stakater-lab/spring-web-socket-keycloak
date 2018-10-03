package io.aurora.socketapp.channel.bootstrap;

import io.aurora.socketapp.channel.domain.SubChannel;
import io.aurora.socketapp.channel.repository.SubChannelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SubChannelBootstrap implements CommandLineRunner
{
    private SubChannelRepository subChannelRepository;

    public SubChannelBootstrap(SubChannelRepository subChannelRepository1)
    {
        this.subChannelRepository = subChannelRepository1;
    }

    @Override
    public void run(String... args) throws Exception
    {
        loadSubSubChannels();
    }

    private void loadSubSubChannels() 
    {/*
        SubChannel subChannel1 = new SubChannel("Ch-sub-6c5b5a2a-add1","Test-SubChannel 1", new ArrayList(),"Ch-6c5b5a2a-add1");
        SubChannel subChannel2 = new SubChannel("Ch-sub-6c5b5a2a-add2","Test-SubChannel 2", new ArrayList(),"Ch-6c5b5a2a-add1");
        SubChannel subChannel3 = new SubChannel("Ch-sub-6c5b5a2a-add3","Test-SubChannel 3", new ArrayList(),"Ch-6c5b5a2a-add2");
        SubChannel subChannel4 = new SubChannel("Ch-sub-6c5b5a2a-add4","Test-SubChannel 4", new ArrayList(),"Ch-6c5b5a2a-add3");

        subChannelRepository.save(subChannel1);
        subChannelRepository.save(subChannel2);
        subChannelRepository.save(subChannel3);
        subChannelRepository.save(subChannel4);
    */}
}
