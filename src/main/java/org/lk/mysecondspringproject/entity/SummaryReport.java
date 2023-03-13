package org.lk.mysecondspringproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Table(name = "summary_report")
public class SummaryReport {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Id
    @Column(name = "summary_id", nullable = false)
    private String summaryId;

    @Column(name = "operation_type" , nullable = false)
    private String operationType;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "invoice_amount" ,nullable = false)
    private Double invoiceAmount;



    @OneToMany(mappedBy = "summaryReport" , fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<ReportDetail> reportDetails;




    public List<ReportDetail> getReportDetails() {
        if (reportDetails == null)
            reportDetails = new ArrayList<>();

        return reportDetails;
    }


    public void modifyAttributeValue(String attributeName, Object newValue) {
        switch (attributeName) {
            case "type":
                this.operationType = (String) newValue;
                break;
            case "date":
                this.date = LocalDate.parse((String) newValue, DATE_TIME_FORMATTER);
                break;
            case "invoice_amount":
                this.invoiceAmount =  Double.valueOf ((String)  newValue);
                break;
        }
    }
}

