package org.lk.mysecondspringproject.controllers;

import org.lk.mysecondspringproject.dtos.OperationDTO;
import org.lk.mysecondspringproject.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;



    @PostMapping
    public ResponseEntity create(@RequestBody OperationDTO operationDTO){

        operationService.create(operationDTO);

        return new ResponseEntity(operationDTO.getSummaryId(), HttpStatus.CREATED);
    }

}
