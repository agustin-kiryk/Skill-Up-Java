package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.FixedTermDepositDto;
import com.alkemy.wallet.entity.FixedTermDepositEntity;
import com.alkemy.wallet.exception.MinDaysException;
import com.alkemy.wallet.exception.ParamNotFound;
import com.alkemy.wallet.mapper.FixedTermDepositMap;
import com.alkemy.wallet.repository.IFixedTermDepositRepository;
import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.IFixedTermDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FixedTermServiceImpl implements IFixedTermDepositService {


    @Autowired
    private FixedTermDepositMap fixedTermDepositMap;
    @Autowired
    private IFixedTermDepositRepository fixedTermDepositRepository;
    @Autowired
    private IAccountService accountService;

    @Override
    public FixedTermDepositDto createNewFixedTerm(FixedTermDepositDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()){
            throw new ParamNotFound("User not logged in");
        }

        FixedTermDepositEntity entity = fixedTermDepositMap.fixedTermDepositDto2Entity(dto);
        entity.setCreationDate(new Date());

        Long minDays = ((entity.getCreationDate().getTime() - entity.getClosingDate().getTime())
                / 86400000);
        if (minDays > 30) {
            throw new MinDaysException("The Closing date cannot be less than 30 days.");
        }

        entity.setInterest(minDays * 0.5);

        accountService.updateBalance(entity.getAccountId(), entity.getAmount());
        FixedTermDepositEntity CreatedFixedDeposit = fixedTermDepositRepository.save(entity);
        return fixedTermDepositMap.fixedTermDepositEntity2Dto(CreatedFixedDeposit);
    }
}

