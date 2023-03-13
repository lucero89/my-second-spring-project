package org.lk.mysecondspringproject.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OperationItemDTO {

    private String code;
    private Integer quantity;


    public OperationItemDTO(){

    }

}
