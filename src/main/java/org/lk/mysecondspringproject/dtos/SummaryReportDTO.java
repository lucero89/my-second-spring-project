package org.lk.mysecondspringproject.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class SummaryReportDTO {

    @JsonAlias("summary_id")
    private String summaryId;

    @JsonAlias("operation_Type")
    private String operationType;
    private String date;

    @JsonAlias("invoice_amount")
    private double invoiceAmount;

    @JsonAlias("report_detail")
    private List<ReportDetailDTO> reportDetailDTOS;


}
