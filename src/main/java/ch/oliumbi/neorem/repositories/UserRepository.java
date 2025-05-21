package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Override
    @Cacheable("users")
    Optional<User> findById(UUID uuid);

    Optional<User> findByName(String name);
}
