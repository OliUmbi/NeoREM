package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
