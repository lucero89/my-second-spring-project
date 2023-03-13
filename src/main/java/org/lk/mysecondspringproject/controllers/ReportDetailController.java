package org.lk.mysecondspringproject.controllers;

import org.lk.mysecondspringproject.dtos.ReportDetailDTO;
import org.lk.mysecondspringproject.service.ReportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/summary-reports/{summaryReportId}/report-details")
public class ReportDetailController {

    @Autowired
    private ReportDetailService reportDetailService;


    @GetMapping("/{reportDetailId}")
    public ResponseEntity retrieveById(@PathVariable String summaryReportId,
                                       @PathVariable Integer reportDetailId){

        ReportDetailDTO reportDetailDTO = reportDetailService.retrieveById(summaryReportId, reportDetailId);

        return new ResponseEntity(reportDetailDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{reportDetailId}")
    public ResponseEntity delete(@PathVariable Integer reportDetailId) {
        reportDetailService.delete(reportDetailId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{reportDetailId}")
    public ResponseEntity replace(@PathVariable String summaryReportId,
                                  @PathVariable Integer reportDetailId,
                                  @RequestBody ReportDetailDTO reportDetailDTO) {

        reportDetailService.replace(summaryReportId, reportDetailId, reportDetailDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{reportDetailId}")
    public ResponseEntity modify(@PathVariable String summaryReportId,
                                 @PathVariable Integer reportDetailId,
                                 @RequestBody Map<String, Object> fieldsToModify) {

        reportDetailService.modify(summaryReportId, reportDetailId, fieldsToModify);

        return new ResponseEntity(HttpStatus.OK);
    }
}
