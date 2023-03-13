package org.lk.mysecondspringproject.controllers;

import org.lk.mysecondspringproject.dtos.SummaryReportDTO;
import org.lk.mysecondspringproject.service.SummaryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/summary-reports")
public class SummaryReportController {

    @Autowired
    private SummaryReportService summaryReportService;


    @PostMapping
    public ResponseEntity create (@RequestBody SummaryReportDTO summaryReportDTO){
        summaryReportService.create((List<SummaryReportDTO>) summaryReportDTO);


        return new ResponseEntity<>(summaryReportDTO.getSummaryId() , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity retrieve(){

        return new ResponseEntity(summaryReportService.retrieveAll(), HttpStatus.OK);
    }

    @GetMapping("/{summaryReportId}")
    public ResponseEntity retrieveById(@PathVariable String summaryReportId){

        SummaryReportDTO summaryReportDTO = summaryReportService.retrieveById(summaryReportId);

        return new ResponseEntity(summaryReportDTO, HttpStatus.OK);

    }

    @PutMapping("/{summaryReportId}")
    public ResponseEntity replace(@PathVariable String summaryReportId,
                                  @RequestBody SummaryReportDTO summaryReportDTO) {
        summaryReportService.replace(summaryReportId, summaryReportDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{summaryReportId}")
    public ResponseEntity modify(@PathVariable String summaryReportId,
                                 @RequestBody Map<String, Object> fieldsToModify) {
        summaryReportService.modify(summaryReportId, fieldsToModify);

        return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("/{summaryReportId}")
    public ResponseEntity delete(@PathVariable String summaryReportId) {
        summaryReportService.delete(summaryReportId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
