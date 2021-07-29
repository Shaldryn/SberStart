package task_5.task_5_3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task_5.task_5_3.entity.Box;

public interface BoxRepository extends JpaRepository<Box, Long> {
}
