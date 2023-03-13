package org.lk.mysecondspringproject.controllers;

import org.lk.mysecondspringproject.dtos.SupplierDTO;
import org.lk.mysecondspringproject.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;


    @PostMapping
    public ResponseEntity create (@RequestBody SupplierDTO supplierDTO){

        supplierService.create(supplierDTO);

        return new ResponseEntity(supplierDTO.getId(), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity retrieve(){

        return new ResponseEntity(supplierService.retrieveAll(), HttpStatus.OK);
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity retrieveByIdWithItems(@PathVariable String supplierId){

        SupplierDTO supplierDTO = supplierService.retrieveByIdWithItems(supplierId);

        return new ResponseEntity(supplierDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity delete(@PathVariable String supplierId) {
        supplierService.delete(supplierId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity replace(@PathVariable String supplierId,
                                  @RequestBody SupplierDTO supplierDTO) {

        supplierService.replace(supplierId, supplierDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{supplierId}")
    public ResponseEntity modify(@PathVariable String supplierId,
                                 @RequestBody Map<String, Object> fieldsToModify) {
        supplierService.modify(supplierId, fieldsToModify);

        return new ResponseEntity(HttpStatus.OK);
    }
}
