package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.FixedTermDepositDto;
import com.alkemy.wallet.dto.FixedTermSimulationDto;
import com.alkemy.wallet.entity.AccountEntity;
import com.alkemy.wallet.entity.FixedTermDepositEntity;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.mapper.exception.MinDaysException;
import com.alkemy.wallet.mapper.FixedTermDepositMap;
import com.alkemy.wallet.repository.IAccountRepository;
import com.alkemy.wallet.repository.IFixedTermDepositRepository;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.IFixedTermDepositService;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

  @Autowired
  private IUserRepository userRepository;
  @Autowired
  private IAccountRepository accountRepository;

  @Override
  public FixedTermDepositDto createNewFixedTerm(String currency, FixedTermDepositDto dto) {

    FixedTermDepositEntity entity = fixedTermDepositMap.fixedTermDepositDto2Entity(dto);

    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = userRepository.findByEmail(email);
    entity.setUserId(user.getUserId());

    AccountEntity account = accountRepository.findByCurrencyAndUser(Currency.valueOf(currency),
        user);
    entity.setAccountId(account.getAccountId());
    entity.setCreationDate(new Date());

    Long minDays = ((entity.getCreationDate().getTime() - entity.getClosingDate().getTime())
        / 86400000);
    if (minDays > 30) {
      throw new MinDaysException("The Closing date cannot be less than 30 days.");
    }

    entity.setInterest((minDays * 0.5) * -1);

    Long id = 20L;

    accountService.updateBalance(entity.getAccountId(), entity.getAmount());
    FixedTermDepositEntity CreatedFixedDeposit = fixedTermDepositRepository.save(entity);
    return fixedTermDepositMap.fixedTermDepositEntity2Dto(CreatedFixedDeposit);
  }

  @Override
  public FixedTermSimulationDto simulateFixedTermDeposit(FixedTermSimulationDto dto) {

    if(dto.getCreationDate() == null){
      throw new MinDaysException("You must choose a closing date");
    }

Date date = new Date();
    Long diffInMillies = Math.abs(dto.getClosingDate().getTime() -
        date.getTime());

    double diff = TimeUnit.DAYS.convert(diffInMillies,
            TimeUnit.MILLISECONDS);

    if (diff < 30) {
      throw new MinDaysException("The Closing date cannot be less than 30 days.");
    }

    FixedTermSimulationDto simulation = new FixedTermSimulationDto();
    simulation.setClosingDate(dto.getClosingDate());
    simulation.setCreationDate(date);
    simulation.setAmount(dto.getAmount());
    simulation.setInterest(diff * 0.5);
    //                        (AMOUNT DEPOSITED)      +   (INTEREST EARNINGS CALCULATED WITH A SIMPLE THREE RULE)

    simulation.setTotalAmount(
        (simulation.getAmount()) + ((simulation.getAmount() * simulation.getInterest()) / 100));

    return simulation;
  }
}

