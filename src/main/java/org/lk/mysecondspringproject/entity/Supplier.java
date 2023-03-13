package org.lk.mysecondspringproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Table(name = "supplier")

public class Supplier {

    @Id
    private String id;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<Item> items;



    public List<Item> getItems() {
        if (items == null)
            items = new ArrayList<>();

        return items;
    }



    public void modifyAttributeValue(String attributeName, Object newValue) {
        switch (attributeName) {
            case "company":
                this.company = (String) newValue;
                break;
            case "address":
                this.address = (String) newValue;
                break;
            case "contact":
                this.contact = (String) newValue;
                break;
            case "status":
                this.status = (String) newValue;
                break;

        }
    }
}
