package task_5.task_5_3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import task_5.task_5_3.controller.dto.BoxRequestDTO;
import task_5.task_5_3.controller.dto.BoxResponseDTO;
import task_5.task_5_3.controller.dto.DocumentRequestDTO;
import task_5.task_5_3.entity.Document;
import task_5.task_5_3.service.BoxService;

import java.util.stream.Collectors;

@RestController
public class BoxController {

    private final BoxService boxService;

    @Autowired
    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping("/boxes/{boxId}")
    public BoxResponseDTO getById(@PathVariable Long boxId) {
        return new BoxResponseDTO(boxService.getById(boxId));
    }

    @PostMapping("/boxes")
    public Long create(@RequestBody BoxRequestDTO boxRequestDTO) {
        return boxService.save(boxRequestDTO.getName(), boxRequestDTO.getBarcode(), boxRequestDTO.getDocuments()
                .stream()
                .map(DocumentRequestDTO -> new Document(DocumentRequestDTO.getName(), DocumentRequestDTO.getBarcode()))
                .collect(Collectors.toList()));
    }

}
