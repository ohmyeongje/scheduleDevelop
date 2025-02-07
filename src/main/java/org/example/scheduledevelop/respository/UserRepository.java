package org.example.scheduledevelop.respository;

import org.example.scheduledevelop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
