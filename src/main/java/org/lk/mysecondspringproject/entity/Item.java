package org.lk.mysecondspringproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Entity
@Builder
@Getter
@Table(name = "item")
public class Item {

    @Id
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String status;

    private String description;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<ReportDetail> reportDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;



    public void modifyAttributeValue(String attributeName, Object newValue) {
        switch (attributeName) {
            case "name":
                this.name = (String) newValue;
                break;
            case "stock":
                this.stock = (Integer) newValue;
                break;
            case "price":
                this.price = (Double) newValue;
                break;
            case "status":
                this.status = (String) newValue;
                break;
            case "description":
                this.description = (String) newValue;
                break;
        }
    }
}
