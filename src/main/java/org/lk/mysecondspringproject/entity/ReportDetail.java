package org.lk.mysecondspringproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;


@Entity
@Builder
@Getter
@Table(name = "report_detail")
public class ReportDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_code", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "summary_report_id", nullable = false)
    private SummaryReport summaryReport;


    public void modifyAttributeValue(String attributeName, Object newValue) {
        switch (attributeName) {
            case "quantity":
                this.quantity = (Integer) newValue;
                break;

        }
    }


}
