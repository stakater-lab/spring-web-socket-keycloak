package io.aurora.socketapp.channel.repository;

import io.aurora.socketapp.channel.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, String>
{
    List<User> findByOnline(boolean status);

    Optional<User> findBySessionIdsEquals(String sessionId);
}
