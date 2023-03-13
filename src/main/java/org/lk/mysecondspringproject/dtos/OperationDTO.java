package org.lk.mysecondspringproject.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OperationDTO {


    @JsonAlias("summary_id")
    private String summaryId;

    @JsonAlias("type")
    private String operationType;

    private String date;

    @JsonAlias("invoice_amount")
    private double invoiceAmount;

    @JsonAlias("items")
    private List<OperationItemDTO> itemDTOS;

    public OperationDTO(){

    }

}
