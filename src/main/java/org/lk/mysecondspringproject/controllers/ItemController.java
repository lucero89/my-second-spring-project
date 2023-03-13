package org.lk.mysecondspringproject.controllers;

import org.lk.mysecondspringproject.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/suppliers/{supplierId}/items")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @PostMapping
    public ResponseEntity create(@PathVariable String supplierId,
                                 @RequestBody ItemDTO itemDTO){

        itemService.create(itemDTO, supplierId);

        return new ResponseEntity(itemDTO.getCode(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity retrieve(@PathVariable String supplierId){

        return new ResponseEntity(itemService.retrieveAll(supplierId), HttpStatus.OK);
    }

    @GetMapping("/{itemCode}")
    public ResponseEntity retrieveById(@PathVariable String itemCode){

        ItemDTO itemDTO = itemService.retrieveByCode(itemCode);

        return new ResponseEntity(itemDTO, HttpStatus.OK);

    }


    @DeleteMapping("/{itemCode}")
    public ResponseEntity delete(@PathVariable String itemCode){
        itemService.delete(itemCode);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{itemCode}")
    public ResponseEntity replace(@PathVariable String supplierId,
                                  @PathVariable String itemCode,
                                  @RequestBody ItemDTO itemDTO) {
        itemService.replace(supplierId, itemCode, itemDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{itemCode}")
    public ResponseEntity modify(@PathVariable String supplierId,
                                 @PathVariable String itemCode,
                                 @RequestBody Map<String, Object> fieldsToModify) {

        itemService.modify(supplierId,itemCode, fieldsToModify);

        return new ResponseEntity(HttpStatus.OK);
    }

}
