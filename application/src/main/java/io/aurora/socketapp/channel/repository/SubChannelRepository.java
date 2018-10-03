package io.aurora.socketapp.channel.repository;

import io.aurora.socketapp.channel.domain.SubChannel;
import org.springframework.data.repository.CrudRepository;

public interface SubChannelRepository  extends CrudRepository<SubChannel, String>
{
}
