package dit_tasks.task_5.task_5_3.controller;

import dit_tasks.task_5.task_5_3.service.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationsController {

    private final OperationsService operationsService;

    @Autowired
    public OperationsController(OperationsService operationsService) {
        this.operationsService = operationsService;
    }

    @GetMapping("/documents/{documentId}/extract")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void extractDocumentFromBox(@PathVariable Long documentId) {
        operationsService.extract(documentId);
    }

    @GetMapping("/documents/{documentId}/boxes/{boxId}/put")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void putDocumentInBox(@PathVariable Long documentId, @PathVariable Long boxId) {
        operationsService.put(documentId, boxId);
    }
}
