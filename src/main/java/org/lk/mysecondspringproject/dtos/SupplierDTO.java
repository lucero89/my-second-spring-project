package org.lk.mysecondspringproject.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SupplierDTO {


    private String id;
    private String company;
    private String address;
    private String contact;
    private String status;

    private List<ItemDTO> itemDTOS;


}
