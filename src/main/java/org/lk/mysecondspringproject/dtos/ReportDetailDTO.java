package org.lk.mysecondspringproject.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class ReportDetailDTO {

    private Integer id;

    private Integer quantity;

    private String item;

    @JsonAlias("item_code")
    private String itemCode;

    @JsonAlias("operation-type")
    private String operationType;



    public ReportDetailDTO() {

    }

    public ReportDetailDTO(int quantity) {

        this.quantity = quantity;
    }


}
