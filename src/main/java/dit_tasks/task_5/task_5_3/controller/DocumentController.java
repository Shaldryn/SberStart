package dit_tasks.task_5.task_5_3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import dit_tasks.task_5.task_5_3.controller.dto.DocumentRequestDTO;
import dit_tasks.task_5.task_5_3.controller.dto.DocumentResponseDTO;
import dit_tasks.task_5.task_5_3.service.DocumentService;

public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/documents/{documentId}")
    public DocumentResponseDTO getById(@PathVariable Long documentId) {
        return new DocumentResponseDTO(documentService.getById(documentId));
    }

    public Long create(@RequestBody DocumentRequestDTO documentRequestDTO) {
        return null;
    }


}
