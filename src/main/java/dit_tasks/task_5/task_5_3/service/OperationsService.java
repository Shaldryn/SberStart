package dit_tasks.task_5.task_5_3.service;

import dit_tasks.task_5.task_5_3.entity.Document;
import dit_tasks.task_5.task_5_3.exceptions.BoxNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationsService {

    private final DocumentService documentService;
    private final BoxService boxService;

    @Autowired
    public OperationsService(DocumentService documentService, BoxService boxService) {
        this.documentService = documentService;
        this.boxService = boxService;
    }


    public void extract(Long documentId) {
        Document document = documentService.getById(documentId);
        if (document.getBox() == null) {
            throw new BoxNotFoundException("the document is not in the box!");
        }
        document.setBox(null);
        documentService.update(documentId, document.getName(), document.getBarcode());
    }

    public void put(Long documentId, Long boxId) {
        Document document = documentService.getById(documentId);
        document.setBox(boxService.getById(boxId));
        documentService.update(documentId, document.getName(), document.getBarcode());
    }
}