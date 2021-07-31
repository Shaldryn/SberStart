package dit_tasks.task_5.task_5_3.controller;

import dit_tasks.task_5.task_5_3.controller.dto.DocumentRequestDTO;
import dit_tasks.task_5.task_5_3.service.BoxService;
import dit_tasks.task_5.task_5_3.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/operations")
public class operationsController {

    private final BoxService boxService;
    private final DocumentService documentService;

    @Autowired
    public operationsController(BoxService boxService, DocumentService documentService) {
        this.boxService = boxService;
        this.documentService = documentService;
    }

//    @PutMapping("/boxes/{documentId}")
//    public ResponseEntity<?> updateDocument(@PathVariable Long documentId, @RequestBody DocumentRequestDTO documentRequestDTO) {
//        documentService.update(documentId, documentRequestDTO.getName(), documentRequestDTO.getBarcode());
//        return ResponseEntity.ok("document updated");
//    }
}
