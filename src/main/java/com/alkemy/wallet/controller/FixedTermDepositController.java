package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.FixedTermDepositDto;
import com.alkemy.wallet.dto.FixedTermSimulationDto;
import com.alkemy.wallet.service.IFixedTermDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("investments")
public class FixedTermDepositController {

    @Autowired
    private IFixedTermDepositService fixedTermDepositService;

    @PostMapping("/fixedDeposit")
    public ResponseEntity<FixedTermDepositDto> CreateNewFixedDeposit(@RequestParam String currency,
            @RequestBody FixedTermDepositDto dto) {
        FixedTermDepositDto newFixedTerm = fixedTermDepositService.createNewFixedTerm(currency,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFixedTerm);
    }

    @GetMapping("/fixedTermDeposit/simulate")
    public ResponseEntity<FixedTermSimulationDto> simulateFixedTerm(@RequestBody FixedTermSimulationDto dto){

        FixedTermSimulationDto simulation = fixedTermDepositService.simulateFixedTermDeposit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
