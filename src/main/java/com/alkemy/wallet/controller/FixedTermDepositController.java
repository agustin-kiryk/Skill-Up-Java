package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.FixedTermDepositDto;
import com.alkemy.wallet.service.IFixedTermDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("investments")
public class FixedTermDepositController {

    @Autowired
    private IFixedTermDepositService fixedTermDepositService;

    @PostMapping("/fixedDeposit")
    public ResponseEntity<FixedTermDepositDto> CreateNewFixedDeposit(
            @RequestBody FixedTermDepositDto dto) {
        FixedTermDepositDto newFixedTerm = fixedTermDepositService.createNewFixedTerm(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFixedTerm);
    }

}
