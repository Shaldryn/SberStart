package dit_tasks.task_5.task_5_3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dit_tasks.task_5.task_5_3.entity.Document;
import dit_tasks.task_5.task_5_3.exceptions.DocumentNotFoundException;
import dit_tasks.task_5.task_5_3.repository.DocumentRepository;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document getById(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Unable to find document with id: " + documentId));
    }
}
