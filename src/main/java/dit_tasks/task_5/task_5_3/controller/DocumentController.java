package dit_tasks.task_5.task_5_3.controller;

import dit_tasks.task_5.task_5_3.controller.dto.DocumentRequestDTO;
import dit_tasks.task_5.task_5_3.controller.dto.DocumentResponseDTO;
import dit_tasks.task_5.task_5_3.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentResponseDTO> getAllDocuments() {
        return documentService.getAll().stream()
                .map(DocumentResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/documents/{documentId}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentResponseDTO getDocumentById(@PathVariable Long documentId) {
        return new DocumentResponseDTO(documentService.getById(documentId));
    }

    @PostMapping("/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createDocument(@RequestBody DocumentRequestDTO documentRequestDTO) {
        return documentService.save(documentRequestDTO.getName(), documentRequestDTO.getBarcode());
    }

    @PutMapping("/documents/{documentId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateDocument(@PathVariable Long documentId, @RequestBody DocumentRequestDTO documentRequestDTO) {
        documentService.update(documentId, documentRequestDTO.getName(), documentRequestDTO.getBarcode());
    }

    @GetMapping("/documents/{documentId}/extract")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void extractDocumentFromBox(@PathVariable Long documentId) {
        documentService.extract(documentId);
    }

    @GetMapping("/documents/{documentId}/boxes/{boxId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void putDocumentInBox(@PathVariable Long documentId, @PathVariable Long boxId) {
        documentService.put(documentId, boxId);
    }
}
