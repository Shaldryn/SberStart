package dit_tasks.task_5.task_5_3.repository;

import dit_tasks.task_5.task_5_3.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
