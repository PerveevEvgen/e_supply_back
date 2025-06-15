package ua.kpi.diploma.e_supply.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.diploma.e_supply.dto.DisposalRequestDTO;
import ua.kpi.diploma.e_supply.dto.ItemsDTO;
import ua.kpi.diploma.e_supply.service.DisposalService;

import java.util.List;

@RestController
@RequestMapping("/api/disposal")
@RequiredArgsConstructor
public class DisposalController {

    private final DisposalService disposalService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ItemsDTO> disposeItems(@RequestPart("request") @Valid DisposalRequestDTO request,
                                       @RequestPart("file") MultipartFile file) {
        return disposalService.disposeItems(request, file);
        //return ResponseEntity.ok("Items disposed successfully.");
    }
}
