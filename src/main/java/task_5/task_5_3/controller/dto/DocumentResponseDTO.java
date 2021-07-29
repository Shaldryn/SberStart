package task_5.task_5_3.controller.dto;

import task_5.task_5_3.entity.Document;

import javax.persistence.Column;

public class DocumentResponseDTO {

    private Long id;

    private String name;

    private String barcode;

    public DocumentResponseDTO(Document document) {
        this.id = document.getId();
        this.name = document.getName();
        this.barcode = document.getBarcode();
    }

    public DocumentResponseDTO(Long id, String name, String barcode) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
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
}
