package org.opensource.repository;

import lombok.AllArgsConstructor;
import org.opensource.user.port.in.command.EmailAuthCommand;
import org.opensource.user.port.out.persistence.EmailAuthPersistencePort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@AllArgsConstructor
public class EmailAuthRedisRepository implements EmailAuthPersistencePort {
    private final StringRedisTemplate redisTemplate;

    @Override
    public void setData(EmailAuthCommand command) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(command.duration());
        valueOperations.set(command.email(), command.authToken(), expireDuration);
    }

    @Override
    public boolean existData(String email) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(email));
    }

    @Override
    public String getData(String email) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(email);
    }

    @Override
    public void deleteData(String email) {
        redisTemplate.delete(email);
    }
}
