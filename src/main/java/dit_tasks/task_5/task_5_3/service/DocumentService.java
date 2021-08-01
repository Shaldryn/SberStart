package dit_tasks.task_5.task_5_3.service;

import dit_tasks.task_5.task_5_3.entity.Document;
import dit_tasks.task_5.task_5_3.exceptions.DocumentNotFoundException;
import dit_tasks.task_5.task_5_3.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> getAll() {
        return documentRepository.findAll();
    }

    public Document getById(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Unable to find document with id: " + documentId));
    }

    public Long save(String name, String barcode) {
        Document document = new Document(name, barcode);
        return documentRepository.save(document).getId();
    }

    public void update(Long id, String name, String barcode) {
        Document document = documentRepository.getById(id);
        document.setName(name);
        document.setBarcode(barcode);
        documentRepository.save(document);
    }

//    public void extract(Long documentId) {
//        Document document = documentRepository.getById(documentId);
//        document.setBox(null);
//        documentRepository.save(document);
//    }
//
//    public void put(Long documentId, Long boxId) {
//        Document document = documentRepository.getById(documentId);
//        Box box = new Box();
//        box.setId(boxId);
//        document.setBox(box);
//        documentRepository.save(document);
//    }
}
