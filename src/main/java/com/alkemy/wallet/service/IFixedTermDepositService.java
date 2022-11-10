package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.FixedTermDepositDto;
import com.alkemy.wallet.dto.FixedTermSimulationDto;

public interface IFixedTermDepositService {

    FixedTermDepositDto createNewFixedTerm(String currency, FixedTermDepositDto dto);

    FixedTermSimulationDto simulateFixedTermDeposit(FixedTermSimulationDto dto);
}
