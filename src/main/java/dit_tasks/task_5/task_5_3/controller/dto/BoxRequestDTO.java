package dit_tasks.task_5.task_5_3.controller.dto;

import java.util.List;

public class BoxRequestDTO {

    private String name;

    private String barcode;

    private List<DocumentRequestDTO> documents;

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public List<DocumentRequestDTO> getDocuments() {
        return documents;
    }
}
