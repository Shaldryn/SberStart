package task_5.task_5_3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task_5.task_5_3.entity.Box;
import task_5.task_5_3.entity.Document;
import task_5.task_5_3.exceptions.BoxNotFoundException;
import task_5.task_5_3.repository.BoxRepository;

import java.util.List;

@Service
public class BoxService {

    private final BoxRepository boxRepository;

    @Autowired
    public BoxService(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    public Long save(String name, String barcode, List<Document> documents) {
        Box box = new Box(name, barcode, documents);
        return boxRepository.save(box).getId();
    }

    public Box getById(Long boxId) {
        return boxRepository.findById(boxId)
                .orElseThrow(() -> new BoxNotFoundException("Unable to find box with id: " + boxId));
    }
}
