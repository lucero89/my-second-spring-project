package org.lk.mysecondspringproject.service;

import org.lk.mysecondspringproject.dtos.ItemDTO;
import org.lk.mysecondspringproject.dtos.OperationDTO;
import org.lk.mysecondspringproject.dtos.OperationItemDTO;
import org.lk.mysecondspringproject.entity.Item;
import org.lk.mysecondspringproject.entity.ReportDetail;
import org.lk.mysecondspringproject.entity.SummaryReport;
import org.lk.mysecondspringproject.exception.ExistingResourceException;
import org.lk.mysecondspringproject.exception.OutOfStockException;
import org.lk.mysecondspringproject.exception.ResourceNotFoundException;
import org.lk.mysecondspringproject.repository.ItemRepository;
import org.lk.mysecondspringproject.repository.ReportDetailRepository;
import org.lk.mysecondspringproject.repository.SummaryReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationService {

    @Autowired
    private SummaryReportRepository summaryReportRepository;

    @Autowired
    private  SummaryReportService summaryReportService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ReportDetailRepository reportDetailRepository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");



    public void create(OperationDTO operationDTO) {

        for (OperationItemDTO operationItemDTO : operationDTO.getItemDTOS()) {
            checkForExistingItem(operationItemDTO.getCode());
        }

        SummaryReport summaryReport = mapToEntity(operationDTO);
        checkForExistingSummaryReport(summaryReport.getSummaryId());
        summaryReport = summaryReportRepository.save(summaryReport);

        List<ReportDetail> reportDetails = mapToEntity(operationDTO.getItemDTOS(), summaryReport);
        if (!reportDetails.isEmpty()) {
            reportDetailRepository.saveAll(reportDetails);
        }
        else {
            throw new ResourceNotFoundException("Por favor ingresar un tipo de operación válido." + "\n" +
                    "Debe indicar si la operación es de 'entrada' o  de 'salida'.");
        }


    }


    private void checkForExistingItem(String code) {
        if (!itemRepository.existsById(code)) {
            throw new ResourceNotFoundException();
        }
    }


    private void checkForExistingSummaryReport(String summaryReportId) {
        if (summaryReportRepository.existsById(summaryReportId)) {
            throw new ExistingResourceException("El Summary Report que está intentando crear ya existe.");
        }
    }


    private List<ReportDetail> mapToEntity(List<OperationItemDTO> itemDTOS, SummaryReport summaryReport) {

        boolean todoOk = true;

        List<ReportDetail> reportDetails = new ArrayList<>();
        for (OperationItemDTO operationItemDTO : itemDTOS) {
            Item item = itemRepository.getById(operationItemDTO.getCode());

            try{
                stockMovementOperation(operationItemDTO, summaryReport.getOperationType(), item);
            }
            catch ( Exception e ) {
                todoOk = false;
            }



            if (todoOk) {
                itemRepository.save(item);
                ReportDetail reportDetail = new ReportDetail(operationItemDTO.getQuantity(), item, summaryReport);
                reportDetails.add(reportDetail);
            }

        }
        return reportDetails;
    }
    private void stockMovementOperation(OperationItemDTO operationItemDTO, String operationType, Item item) {


        if ("entrada".equals(operationType)) {
            item.setStock(item.getStock() + operationItemDTO.getQuantity());
        } else if ("salida".equals(operationType)) {
            if (operationItemDTO.getQuantity() > item.getStock()) {
                throw new OutOfStockException();
            }
            item.setStock(item.getStock() - operationItemDTO.getQuantity());
        } else {
            throw new ResourceNotFoundException("Por favor ingresar un tipo de operación válido." + "\n" +
                    "Debe indicar si la operación es de 'entrada' o  de 'salida'.");


       /* if ("entrada".equals(operationType)) {
            item.setStock(item.getStock() + operationItemDTO.getQuantity());
        } else if ("salida".equals(operationType)) {
            item.setStock(item.getStock() - operationItemDTO.getQuantity());
        } else{
            throw new ResourceNotFoundException();
        }*/

        }

    }

    private SummaryReport mapToEntity(OperationDTO operationDTO) {

        SummaryReport summaryReport = new SummaryReport(operationDTO.getSummaryId(), operationDTO.getOperationType(),
                LocalDate.parse(operationDTO.getDate(), DATE_TIME_FORMATTER), operationDTO.getInvoiceAmount());

        return summaryReport;

    }

    private List<Item> mapToEntity(List<ItemDTO> itemDTOS) {

        return itemDTOS.stream()
                .map(itemDTO -> mapToEntity(itemDTO))
                .collect(Collectors.toList());
    }

    private Item mapToEntity(ItemDTO itemDTO) {
        Item item = new Item(itemDTO.getCode(), itemDTO.getName(), itemDTO.getStock(), itemDTO.getPrice(),
                itemDTO.getStatus(), itemDTO.getDescription());

        return item;
    }


}
