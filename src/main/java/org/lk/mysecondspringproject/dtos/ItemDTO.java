package org.lk.mysecondspringproject.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemDTO {

    private String code;
    private String name;
    private Integer stock;
    private Double price;
    private String status;
    private String description;

}

