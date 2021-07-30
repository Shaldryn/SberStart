package dit_tasks.task_5.task_5_3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dit_tasks.task_5.task_5_3.controller.dto.BoxRequestDTO;
import dit_tasks.task_5.task_5_3.controller.dto.BoxResponseDTO;
import dit_tasks.task_5.task_5_3.controller.dto.DocumentResponseDTO;
import dit_tasks.task_5.task_5_3.entity.Document;
import dit_tasks.task_5.task_5_3.service.BoxService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BoxController {

    private final BoxService boxService;

    @Autowired
    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping("/boxes")
    public List<BoxResponseDTO> getAllBoxes() {
        return boxService.getAll().stream().map(BoxResponseDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/boxes/{boxId}")
    public BoxResponseDTO getBoxById(@PathVariable Long boxId) {
        return new BoxResponseDTO(boxService.getById(boxId));
    }

    @PostMapping("/boxes")
    public Long createBox(@RequestBody BoxRequestDTO boxRequestDTO) {
        return boxService.save(boxRequestDTO.getName(), boxRequestDTO.getBarcode(), boxRequestDTO.getDocuments()
                .stream()
                .map(DocumentRequestDTO -> new Document(DocumentRequestDTO.getName(), DocumentRequestDTO.getBarcode()))
                .collect(Collectors.toList()));
    }

    @PutMapping("/boxes/{boxId}")
    public ResponseEntity<?> updateBox(@PathVariable Long boxId, @RequestBody BoxRequestDTO boxRequestDTO) {
        boxService.update(boxId, boxRequestDTO.getName(), boxRequestDTO.getBarcode(),
                boxRequestDTO.getDocuments().stream()
                        .map(DocumentRequestDTO -> new Document(DocumentRequestDTO.getName(), DocumentRequestDTO.getBarcode()))
                        .collect(Collectors.toList()));

        return ResponseEntity.ok("box updated");
    }

}
