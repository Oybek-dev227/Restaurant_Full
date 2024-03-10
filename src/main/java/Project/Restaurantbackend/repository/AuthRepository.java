package Project.Restaurantbackend.repository;

import Project.Restaurantbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByPhoneNumber(String phoneNumber);

    boolean existsUserByPhoneNumber(String phoneNumber);
}
