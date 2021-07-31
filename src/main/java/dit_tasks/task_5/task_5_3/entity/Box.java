package dit_tasks.task_5.task_5_3.entity;

import dit_tasks.task_5.task_5_3.controller.dto.BoxRequestDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "boxes")
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "barcode")
    private String barcode;

//    @OneToMany(mappedBy = "box", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "box", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Document> documents;

    public Box() {
    }

    public Box(BoxRequestDTO boxRequestDTO) {
        this.name = boxRequestDTO.getName();
        this.barcode = boxRequestDTO.getBarcode();
    }

    public Box(String name, String barcode) {
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

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        if (documents == null) {
            documents = new ArrayList<>();
        }

        documents.forEach(this::addDocument);

    }

    public void addDocument(Document document) {
        if (documents == null) {
            documents = new ArrayList<>();
        }

        documents.add(document);
        document.setBox(this);
    }

    public void deleteDocument(Document document) {
        if (documents == null) {
            documents = new ArrayList<>();
        }

        documents.remove(document);
        document.setBox(null);
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", barcode='" + barcode + '\'' +
                ", documents=" + documents +
                '}';
    }
}
