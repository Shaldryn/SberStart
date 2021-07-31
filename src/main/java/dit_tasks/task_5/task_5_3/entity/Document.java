package dit_tasks.task_5.task_5_3.entity;

import javax.persistence.*;

@Entity(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "barcode")
    private String barcode;

//    @ManyToOne(cascade = {
//            CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "box_id")
    private Box box;

    public Document() {
    }

    public Document(String name, String barcode) {
        this.name = name;
        this.barcode = barcode;
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

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", barcode='" + barcode + '\'' +
                '}';
    }
}
