package org.example.scheduledevelop.respository;

import org.example.scheduledevelop.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
