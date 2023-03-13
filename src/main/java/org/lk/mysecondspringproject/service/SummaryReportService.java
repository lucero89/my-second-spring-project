package org.lk.mysecondspringproject.service;

import org.lk.mysecondspringproject.dtos.SummaryReportDTO;
import org.lk.mysecondspringproject.entity.SummaryReport;
import org.lk.mysecondspringproject.exception.ResourceNotFoundException;
import org.lk.mysecondspringproject.repository.SummaryReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SummaryReportService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Autowired
    private SummaryReportRepository summaryReportRepository;

    @Autowired
    private  ReportDetailService reportDetailService;


    public  List<SummaryReportDTO> retrieveAll() {
        List<SummaryReport> summaryReports = summaryReportRepository.findAll();
        return summaryReports.stream()
                .map(summaryReport -> mapToDTO(summaryReport))
                .collect(Collectors.toList());
    }

    public void create (List<SummaryReportDTO> summaryReportDTOS ){ //recibe ese proveedor creado en la bd, crea una lista de summary
        List<SummaryReport> summaryReports = summaryReportDTOS.stream()
                .map(summaryReportDTO -> mapToEntity(summaryReportDTO))
                .collect(Collectors.toList());
        summaryReportRepository.saveAll(summaryReports);
    }

    public SummaryReportDTO retrieveById(String summaryReportId) {
        Optional<SummaryReport> summaryReport = summaryReportRepository.findById(summaryReportId);

        if (summaryReport.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return mapToDTO(summaryReport.get());
    }

    public void replace(String summaryReportId, SummaryReportDTO summaryReportDTO) {
        Optional<SummaryReport> summaryReport = summaryReportRepository.findById(summaryReportId);
        if (summaryReport.isEmpty()){
            throw new ResourceNotFoundException();
        }
        SummaryReport summaryReportToReplace = summaryReport.get();
        summaryReportToReplace.setOperationType(summaryReportDTO.getOperationType());
        summaryReportToReplace.setDate(LocalDate.parse(summaryReportDTO.getDate(), DATE_TIME_FORMATTER));
        summaryReportToReplace.setInvoiceAmount(summaryReportDTO.getInvoiceAmount());

        summaryReportRepository.save(summaryReportToReplace);
    }

    public void modify(String summaryReportId, Map<String, Object> fieldsToModify) {
        Optional<SummaryReport> summaryReport = summaryReportRepository.findById(summaryReportId);
        if (summaryReport.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        SummaryReport summaryToModify = summaryReport.get();
        fieldsToModify.forEach((key , value) -> summaryToModify.modifyAttributeValue(key, value));
        summaryReportRepository.save(summaryToModify);
    }


    public void delete(String summaryReportId) {
        try {
            summaryReportRepository.deleteById(summaryReportId);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException();
        }
    }

    public List<SummaryReportDTO> mapToDTOS(List<SummaryReport> summaryReports) {

        return  summaryReports.stream()
                .map(summaryReport -> mapToDTO(summaryReport))
                .collect(Collectors.toList());
    }

    private SummaryReport mapToEntity(SummaryReportDTO summaryReportDTO) {

        SummaryReport summaryReport = new SummaryReport(summaryReportDTO.getSummaryId(),
                summaryReportDTO.getOperationType(), LocalDate.parse(summaryReportDTO.getDate(), DATE_TIME_FORMATTER),
                summaryReportDTO.getInvoiceAmount());

        return  summaryReport;
    }

    private SummaryReportDTO mapToDTO(SummaryReport summaryReport){

        SummaryReportDTO summaryReportDTO = new SummaryReportDTO(summaryReport.getSummaryId(),summaryReport.getOperationType(),
                summaryReport.getDate().toString(), summaryReport.getInvoiceAmount(),
                reportDetailService.mapToDTOS(summaryReport.getReportDetails()));

        return summaryReportDTO;
    }



}

