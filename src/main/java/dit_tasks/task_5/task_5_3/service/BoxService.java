package dit_tasks.task_5.task_5_3.service;

import dit_tasks.task_5.task_5_3.entity.Box;
import dit_tasks.task_5.task_5_3.entity.Document;
import dit_tasks.task_5.task_5_3.exceptions.BoxNotFoundException;
import dit_tasks.task_5.task_5_3.repository.BoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BoxService {

    private final BoxRepository boxRepository;

    @Autowired
    public BoxService(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    public Long save(String name, String barcode, List<Document> documents) {
        Box box = new Box(name, barcode);
        box.setDocuments(documents);
        return boxRepository.save(box).getId();
    }

    public Collection<Box> getAll() {
        return boxRepository.findAll();
    }

    public Box getById(Long boxId) {
        return boxRepository.findById(boxId)
                .orElseThrow(() -> new BoxNotFoundException("Unable to find box with id: " + boxId));
    }

    public Collection<Document> getDocumentsByBoxId(Long boxId) {
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new BoxNotFoundException("Unable to find box with id: " + boxId));

        return box.getDocuments();
    }

    public void update(Long id, String name, String barcode) {
        Box box = boxRepository.getById(id);
        box.setName(name);
        box.setBarcode(barcode);
        boxRepository.save(box);
    }
}
