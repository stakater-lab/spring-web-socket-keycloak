package io.aurora.socketapp.channel.repository;

import io.aurora.socketapp.channel.domain.Channel;
import org.springframework.data.repository.CrudRepository;



public interface ChannelRepository extends CrudRepository<Channel, String>
{
}
