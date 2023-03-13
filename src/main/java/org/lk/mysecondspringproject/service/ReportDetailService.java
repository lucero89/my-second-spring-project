package org.lk.mysecondspringproject.service;

import org.lk.mysecondspringproject.dtos.ReportDetailDTO;
import org.lk.mysecondspringproject.entity.Item;
import org.lk.mysecondspringproject.entity.ReportDetail;
import org.lk.mysecondspringproject.entity.SummaryReport;
import org.lk.mysecondspringproject.exception.ResourceNotFoundException;
import org.lk.mysecondspringproject.repository.ItemRepository;
import org.lk.mysecondspringproject.repository.ReportDetailRepository;
import org.lk.mysecondspringproject.repository.SummaryReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportDetailService {

    @Autowired
    private ReportDetailRepository reportDetailRepository;
    @Autowired
    private SummaryReportRepository summaryReportRepository;
    @Autowired
    private ItemRepository itemRepository;


    public ReportDetailDTO retrieveById(String summaryReportId, Integer reportDetailId) {
        if (!summaryReportRepository.existsById(summaryReportId)) {
            throw new ResourceNotFoundException("El id del Summary Report que está buscando no existe.");
        }

        Optional<ReportDetail> reportDetail = reportDetailRepository.findById(reportDetailId);

        if (reportDetail.isEmpty()) {
            throw new ResourceNotFoundException("El id del reporte detalle que está buscando no existe.");
        }

        return mapToDTO(reportDetail.get());
    }

    public void delete(Integer reportDetailId) {
        try {
            reportDetailRepository.deleteById(reportDetailId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException();
        }
    }

    public List<ReportDetailDTO> mapToDTOS(List<ReportDetail> reportDetails) {

        return reportDetails.stream()
                .map(reportDetail -> mapToDTO(reportDetail))
                .collect(Collectors.toList());

    }


    public void replace(String summaryReportId, Integer reportDetailId, ReportDetailDTO reportDetailDTO) {
        Optional<SummaryReport> summaryReport = summaryReportRepository.findById(summaryReportId);
        if (summaryReport.isEmpty()) {
            throw new ResourceNotFoundException("El id del Summary Report que está ingresando no existe.");
        }
        Optional<ReportDetail> reportDetail = reportDetailRepository.findById(reportDetailId);
        if (reportDetail.isEmpty()) {
            throw new ResourceNotFoundException("El id del Report Detail que está ingresando no existe.");
        }
        ReportDetail reportDetailToReplace = reportDetail.get();
        reportDetailToReplace.setQuantity(reportDetailDTO.getQuantity());

        reportDetailRepository.save(reportDetailToReplace);
    }

    public void modify(String summaryReportId, Integer reportDetailId, Map<String, Object> fieldsToModify) {
        Optional<SummaryReport> summaryReport = summaryReportRepository.findById(summaryReportId);
        if (summaryReport.isEmpty()) {
            throw new ResourceNotFoundException("El id del Summary Report que está ingresando no existe.");
        }
        Optional<ReportDetail> reportDetail = reportDetailRepository.findById(reportDetailId);
        if (reportDetail.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        ReportDetail reportDetailToModify = reportDetail.get();
        fieldsToModify.forEach((key, value) -> reportDetailToModify.modifyAttributeValue(key, value));
        reportDetailRepository.save(reportDetailToModify);
    }


    private ReportDetailDTO mapToDTO(ReportDetail reportDetail) {

        ReportDetailDTO reportDetailDTO = new ReportDetailDTO(reportDetail.getQuantity(),
                reportDetail.getItem().getName(), reportDetail.getItem().getCode(),
                reportDetail.getSummaryReport().getOperationType());

        reportDetailDTO.setId(reportDetail.getId());
        return reportDetailDTO;
    }

    private ReportDetail mapToEntity(ReportDetailDTO reportDetailDTO, Item item) {

        ReportDetail reportDetail = new ReportDetail(reportDetailDTO.getQuantity(), item);
        return reportDetail;
    }

    private ReportDetail mapToEntity(ReportDetailDTO reportDetailDTO, SummaryReport summaryReport) {
        ReportDetail reportDetail = new ReportDetail(reportDetailDTO.getQuantity(), summaryReport);

        return reportDetail;
    }


}
