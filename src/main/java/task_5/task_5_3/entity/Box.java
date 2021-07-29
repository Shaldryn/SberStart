package task_5.task_5_3.entity;

import task_5.task_5_3.controller.dto.BoxRequestDTO;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "box_id")
    private List<Document> documents;

    public Box() {
    }

    public Box(BoxRequestDTO boxRequestDTO) {
        this.name = boxRequestDTO.getName();
        this.barcode = boxRequestDTO.getBarcode();
    }

    public Box(String name, String barcode, List<Document> documents) {
        this.name = name;
        this.barcode = barcode;
        this.documents = documents;
    }

    public Long getId() {
        return id;
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
        this.documents = documents;
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
