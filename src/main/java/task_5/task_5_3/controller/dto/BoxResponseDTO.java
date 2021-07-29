package task_5.task_5_3.controller.dto;

import task_5.task_5_3.entity.Box;

import javax.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;

public class BoxResponseDTO {

    private Long id;

    private String name;

    private String barcode;

    private List<DocumentResponseDTO> documents;

    public BoxResponseDTO(Box box) {
        this.id = box.getId();
        this.name = box.getName();
        this.barcode = box.getBarcode();
        this.documents = box.getDocuments()
                .stream()
                .map(DocumentResponseDTO::new)
                .collect(Collectors.toList());
    }

    public BoxResponseDTO(Long id, String name, String barcode, List<DocumentResponseDTO> documents) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.documents = documents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public List<DocumentResponseDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentResponseDTO> documents) {
        this.documents = documents;
    }
}
