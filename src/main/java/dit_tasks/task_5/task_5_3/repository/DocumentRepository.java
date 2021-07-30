package dit_tasks.task_5.task_5_3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dit_tasks.task_5.task_5_3.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
