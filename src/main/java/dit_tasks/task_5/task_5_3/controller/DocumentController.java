package dit_tasks.task_5.task_5_3.controller;

import dit_tasks.task_5.task_5_3.controller.dto.DocumentRequestDTO;
import dit_tasks.task_5.task_5_3.controller.dto.DocumentResponseDTO;
import dit_tasks.task_5.task_5_3.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/documents")
    public List<DocumentResponseDTO> getAllDocuments() {
        return documentService.getAll().stream()
                .map(DocumentResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/documents/{documentId}")
    public DocumentResponseDTO getDocumentById(@PathVariable Long documentId) {
        return new DocumentResponseDTO(documentService.getById(documentId));
    }

    @PostMapping("/documents")
    public Long createDocument(@RequestBody DocumentRequestDTO documentRequestDTO) {
        return documentService.save(documentRequestDTO.getName(), documentRequestDTO.getBarcode());
    }

    @PutMapping("/documents/{documentId}")
    public ResponseEntity<?> updateDocument(@PathVariable Long documentId, @RequestBody DocumentRequestDTO documentRequestDTO) {
        documentService.update(documentId, documentRequestDTO.getName(), documentRequestDTO.getBarcode());
        return ResponseEntity.ok("document updated");
    }


}
