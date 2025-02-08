package org.example.scheduledevelop.respository;

import org.example.scheduledevelop.entity.Schedule;
import org.example.scheduledevelop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    default User findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    Optional<User> findByUsername(String username);

    default User findUsernameByElseThrow(String username) {
        return findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist username = " + username));
    }
}
