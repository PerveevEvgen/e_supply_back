package ua.kpi.diploma.e_supply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.diploma.e_supply.dto.ItemsDTO;
import ua.kpi.diploma.e_supply.dto.transferDto.TransferRequestDTO;
import ua.kpi.diploma.e_supply.service.impl.TransferService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> transferItems(
            @RequestPart("request") @Valid TransferRequestDTO request,
            @RequestPart("file") MultipartFile file
    ) {
        transferService.transferItems(request, file);
        return ResponseEntity.ok("Items transferred successfully.");
    }

    @GetMapping
    public ResponseEntity<List<ItemsDTO>> getAvailableForTransferItems() {
        return ResponseEntity.ok(transferService.getAvailableForTransferItems());
    }
}
